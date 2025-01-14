package com.android.identity.server.openid4vci;

import com.android.identity.cbor.Bstr
import com.android.identity.cbor.Cbor
import com.android.identity.cbor.CborArray
import com.android.identity.cbor.CborMap
import com.android.identity.cbor.DataItem
import com.android.identity.cbor.Tagged
import com.android.identity.cbor.Tstr
import com.android.identity.cbor.toDataItem
import com.android.identity.cbor.toDataItemFullDate
import com.android.identity.cose.Cose
import com.android.identity.cose.CoseLabel
import com.android.identity.cose.CoseNumberLabel
import com.android.identity.crypto.Algorithm
import com.android.identity.crypto.EcPrivateKey
import com.android.identity.flow.server.FlowEnvironment
import com.android.identity.crypto.EcPublicKey
import com.android.identity.crypto.X509Cert
import com.android.identity.crypto.X509CertChain
import com.android.identity.document.NameSpacedData
import com.android.identity.documenttype.knowntypes.DVLAVehicleRegistration
import com.android.identity.documenttype.knowntypes.DrivingLicense
import com.android.identity.documenttype.knowntypes.EUPersonalID
import com.android.identity.documenttype.knowntypes.UtopiaNaturalization
import com.android.identity.flow.server.Resources
import com.android.identity.mdoc.mso.MobileSecurityObjectGenerator
import com.android.identity.mdoc.mso.StaticAuthDataGenerator
import com.android.identity.mdoc.util.MdocUtil
import com.android.identity.sdjwt.Issuer
import com.android.identity.sdjwt.SdJwtVcGenerator
import com.android.identity.util.toBase64Url
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.random.Random
import kotlin.time.Duration.Companion.days

internal class CredentialFactoryVRC : CredentialFactory {
    override val offerId: String
    get() = "vrc"

    override val scope: String
    get() = "vrc"

    override val format: Openid4VciFormat
    get() = openId4VciFormatVrc

    override val proofSigningAlgorithms: List<String>
    get() = CredentialFactory.DEFAULT_PROOF_SIGNING_ALGORITHMS

    override val cryptographicBindingMethods: List<String>
    get() = listOf("cose_key")

    override val credentialSigningAlgorithms: List<String>
    get() = CredentialFactory.DEFAULT_CREDENTIAL_SIGNING_ALGORITHMS

    override val name: String
    get() = "Vehicle Registration Certificate (VRC)"

    override val logo: String
    get() = "dvla_vrc.png"

    override suspend fun makeCredential(
            environment: FlowEnvironment,
            state: IssuanceState,
            authenticationKey: EcPublicKey?
    ): String {
        val now = Clock.System.now()

        // Create AuthKeys and MSOs, make sure they're valid for 30 days. Also make
        // sure to not use fractional seconds as 18013-5 calls for this (clauses 7.1
        // and 9.1.2.4)
        //
        val timeSigned = Instant.fromEpochSeconds(now.epochSeconds, 0)
        val validFrom = Instant.fromEpochSeconds(now.epochSeconds, 0)
        val validUntil = validFrom + 30.days

        // Generate an MSO and issuer-signed data for this authentication key.
        val docType = DVLAVehicleRegistration.VRC_DOCTYPE
        val msoGenerator = MobileSecurityObjectGenerator(
            "SHA-256",
            docType,
            authenticationKey!!
        )
        msoGenerator.setValidityInfo(timeSigned, validFrom, validUntil, null)

        val credentialData = NameSpacedData.Builder()

        // As we do not have driver license database, just make up some data to fill mDL
        // for demo purposes. Take what we can from the PID that was presented as evidence.
//        val source = state.credentialData!!
//        val mdocType = DVLAVehicleRegistration.getDocumentType()
//            .mdocDocumentType!!.namespaces[DVLAVehicleRegistration.VRC_NAMESPACE]!!
//        for (elementName in source.getDataElementNames(EUPersonalID.EUPID_NAMESPACE)) {
//            val value = source.getDataElement(EUPersonalID.EUPID_NAMESPACE, elementName)
//            if (mdocType.dataElements.containsKey(elementName)) {
//                credentialData.putEntry(DVLAVehicleRegistration.VRC_NAMESPACE, elementName, value)
//            }
//        }

        credentialData.putEntry(
            DVLAVehicleRegistration.VRC_NAMESPACE,
            "registration_number",
            Cbor.encode("FG67 TUV".toDataItem())
        )

        credentialData.putEntry(
            DVLAVehicleRegistration.VRC_NAMESPACE,
            "date_of_registration",
            Cbor.encode(LocalDate.parse("2020-08-01").toDataItemFullDate())
        )

        credentialData.putEntry(
            DVLAVehicleRegistration.VRC_NAMESPACE,
            "vehicle_identification_number",
            Cbor.encode("WBA3A5C53EF123456".toDataItem())
        )

        credentialData.putEntry(
            DVLAVehicleRegistration.VRC_NAMESPACE,
            "vehicle_holder",
            Cbor.encode(CborMap.builder()
                .put("given_name_unicode", "John")
                .put("family_name_unicode", "Smith")
                .put("resident_address", "Sample Street")
                .put("resident_city", "Sample City")
                .put("resident_country", "ZZ")
                .put("resident_postal_code", "12345").end().build())
        )

        credentialData.putEntry(
            DVLAVehicleRegistration.VRC_NAMESPACE,
            "basic_vehicle_info",
            Cbor.encode(CborMap.builder()
                .put("vehicle_category_code", "M1")
                .put("type_approval_number", "e1-test")
                .put("make", "OPEL")
                .put("commercial_name", "MITSU")
                .put("colours", CborArray.builder().add(4).add(9).end().build()).end().build())
        )

        credentialData.putEntry(
            DVLAVehicleRegistration.VRC_NAMESPACE,
            "mass_info",
            Cbor.encode(CborMap.builder()
                .put("unit", "kg")
                .put("techn_perm_max_laden_mass", 1290)
                .put("vehicle_max_mass", 1150)
                .put("whole_vehicle_max_mass", 2500)
                .put("mass_in_running_order", 920).end().build())
        )

        credentialData.putEntry(
            DVLAVehicleRegistration.VRC_NAMESPACE,
            "engine_info",
            Cbor.encode(CborMap.builder()
                .put("engine_capacity", 999)
                .put("engine_power", 52)
                .put("energy_source", CborArray.builder().add(15).end().build()).end().build())
        )

        credentialData.putEntry(
            DVLAVehicleRegistration.VRC_NAMESPACE,
            "un_distinguishing_sign",
            Cbor.encode("NLD".toDataItem())
        )

        println("credentialData: $credentialData")

        val randomProvider = Random.Default
        val issuerNameSpaces = MdocUtil.generateIssuerNameSpaces(
            credentialData.build(),
            randomProvider,
            16,
            null
        )
        for (nameSpaceName in issuerNameSpaces.keys) {
            val digests = MdocUtil.calculateDigestsForNameSpace(
                nameSpaceName,
                issuerNameSpaces,
                Algorithm.SHA256
            )
            msoGenerator.addDigestIdsForNamespace(nameSpaceName, digests)
        }

        val resources = environment.getInterface(Resources::class)!!
        val documentSigningKeyCert = X509Cert.fromPem(
            resources.getStringResource("ds_certificate.pem")!!)
        val documentSigningKey = EcPrivateKey.fromPem(
            resources.getStringResource("ds_private_key.pem")!!,
            documentSigningKeyCert.ecPublicKey
        )

        val mso = msoGenerator.generate()
        val taggedEncodedMso = Cbor.encode(Tagged(Tagged.ENCODED_CBOR, Bstr(mso)))
        val protectedHeaders = mapOf<CoseLabel, DataItem>(Pair(
            CoseNumberLabel(Cose.COSE_LABEL_ALG),
            Algorithm.ES256.coseAlgorithmIdentifier.toDataItem()
        ))
        val unprotectedHeaders = mapOf<CoseLabel, DataItem>(Pair(
            CoseNumberLabel(Cose.COSE_LABEL_X5CHAIN),
            X509CertChain(listOf(
                X509Cert(documentSigningKeyCert.encodedCertificate)
            )
            ).toDataItem()
        ))
        val encodedIssuerAuth = Cbor.encode(
            Cose.coseSign1Sign(
                documentSigningKey,
                taggedEncodedMso,
                true,
                Algorithm.ES256,
                protectedHeaders,
                unprotectedHeaders
            ).toDataItem()
        )

        val issuerProvidedAuthenticationData = StaticAuthDataGenerator(
            issuerNameSpaces,
            encodedIssuerAuth
        ).generate()

        return issuerProvidedAuthenticationData.toBase64Url()
    }
}
