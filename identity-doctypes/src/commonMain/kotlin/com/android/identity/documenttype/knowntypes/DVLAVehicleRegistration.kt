package com.android.identity.documenttype.knowntypes

import com.android.identity.cbor.Cbor
import com.android.identity.cbor.CborArray
import com.android.identity.cbor.CborMap
import com.android.identity.cbor.Tagged
import com.android.identity.cbor.Tstr
import com.android.identity.cbor.toDataItem
import com.android.identity.cbor.toDataItemFullDate
import com.android.identity.documenttype.DocumentAttributeType
import com.android.identity.documenttype.DocumentType
import com.android.identity.documenttype.Icon
import com.android.identity.documenttype.knowntypes.DrivingLicense.MDL_NAMESPACE
import kotlinx.datetime.LocalDate

object DVLAVehicleRegistration {
    const val VRC_NAMESPACE = "org.iso.7367.1"
    const val VRC_NAMESPACE2 = "org.iso.23220.1"
    const val VRC_DOCTYPE = "org.iso.7367.1.mVRC"

    /**
     * Build the Vehicle Registration Document Type.
     */
    fun getDocumentType(): DocumentType {
        return DocumentType.Builder("DVLA Vehicle Registration")
            .addMdocDocumentType(VRC_DOCTYPE)
            .addVcDocumentType("vc_type")
            .addAttribute(
                DocumentAttributeType.String,
                "registration_number",
                "Vehicle Registration Number",
                "This data element contains the common vehicle registration information, including UN/EU elements, A and H.",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                "FG67 TUV".toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.Date,
                "date_of_registration",
                "Date of Registration",
                "Date when document was registered",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                LocalDate.parse("2024-05-01").toDataItemFullDate()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "vehicle_identification_number",
                "Vehicle Identification Number",
                "Vehicle Identification Number defined by the vehicle manufacturer",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                "1234432112344321".toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.ComplexType,
                "vehicle_holder",
                "Vehicle Holder",
                "The vehicle holder information",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                CborMap.builder()
                    .put("given_name_unicode", "John")
                    .put("family_name_unicode", "Smith")
                    .put("resident_address", "Sample Street")
                    .put("resident_city", "Sample City")
                    .put("resident_country", "ZZ")
                    .put("resident_postal_code", "12345").end().build()
            )
            .addAttribute(
                DocumentAttributeType.ComplexType,
                "basic_vehicle_info",
                "Basic Vehicle Info",
                "Basic vehicle information of the vehicle",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                CborMap.builder()
                    .put("vehicle_category_code", "M1")
                    .put("type_approval_number", "e1-test")
                    .put("make", "OPEL")
                    .put("commercial_name", "MITSU")
                    .put("colours", CborArray.builder().add(4).add(9).end().build()).end().build()
            )
            .addAttribute(
                DocumentAttributeType.ComplexType,
                "mass_info",
                "Mass Info",
                "The mass information of the vehicle",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                CborMap.builder()
                    .put("unit", "kg")
                    .put("techn_perm_max_laden_mass", 1290)
                    .put("vehicle_max_mass", 1150)
                    .put("whole_vehicle_max_mass", 2500)
                    .put("mass_in_running_order", 920).end().build()
            )
            .addAttribute(
                DocumentAttributeType.ComplexType,
                "engine_info",
                "Engine Info",
                "The engine information of the vehicle",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                CborMap.builder()
                    .put("engine_capacity", 999)
                    .put("engine_power", 52)
                    .put("energy_source", CborArray.builder().add(15).end().build()).end().build()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "un_distinguishing_sign",
                "Un distinguishing sign",
                "The un distinguishing sign of the vehicle",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                "NLD".toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "issuing_authority_unicode",
                "Issuing Authority Unicode",
                "The issuing authority of the vehicle",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "GJVLA".toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "issuing_country",
                "Issuing Country",
                "The issuing country of the vehicle",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "GB".toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "issue_date",
                "Issuing Date",
                "The date the vehicle was issued",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "2023-01-15T10:00:00-07:00".toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "expiry_date",
                "Expiry Date",
                "The date the vehicle expires",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "2027-07-07T12:00:00-06:00".toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "document_number",
                "Document Number",
                "The document number of the vehicle",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "54242680014".toDataItem()
            )
            .addSampleRequest(
                id = "registration_number",
                displayName ="Registration Number",
                mdocDataElements = mapOf(
                    VRC_NAMESPACE to mapOf(
                        "registration_number" to false,
                    )
                ),
            )
            .build()
    }
}