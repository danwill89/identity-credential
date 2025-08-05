package org.multipaz.documenttype.knowntypes

import org.multipaz.cbor.CborArray
import org.multipaz.cbor.CborMap
import org.multipaz.cbor.toDataItem
import org.multipaz.cbor.toDataItemFullDate
import org.multipaz.documenttype.DocumentAttributeType
import org.multipaz.documenttype.DocumentType
import org.multipaz.documenttype.Icon
import kotlinx.datetime.LocalDate

object DVLAVehicleRegistration {
    private const val VRC_NAMESPACE = "org.iso.7367.1"
    private const val VRC_NAMESPACE2 = "org.iso.23220.1"
    private const val VRC_DOCTYPE = "org.iso.7367.1.mVRC"

    /**
     * Build the Vehicle Registration Document Type.
     */
    fun getDocumentType(): DocumentType {
        return DocumentType.Builder("DVLA Vehicle Registration")
            .addMdocDocumentType(VRC_DOCTYPE)
            .addMdocAttribute(
                DocumentAttributeType.String,
                "registration_number",
                "Vehicle Registration Number",
                "This data element contains the common vehicle registration information, including UN/EU elements, A and H.",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                "FG67 TUV".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.Date,
                "date_of_registration",
                "Date of Registration",
                "Date when document was registered",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                LocalDate.parse("2024-05-01").toDataItemFullDate()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "vehicle_identification_number",
                "Vehicle Identification Number",
                "Vehicle Identification Number defined by the vehicle manufacturer",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                "1234432112344321".toDataItem()
            )
            .addMdocAttribute(
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
            .addMdocAttribute(
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
            .addMdocAttribute(
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
            .addMdocAttribute(
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
            .addMdocAttribute(
                DocumentAttributeType.String,
                "un_distinguishing_sign",
                "Un distinguishing sign",
                "The un distinguishing sign of the vehicle",
                true,
                VRC_NAMESPACE,
                Icon.PLACE,
                "NLD".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "issuing_authority_unicode",
                "Issuing Authority Unicode",
                "The issuing authority of the vehicle",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "GJVLA".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "issuing_country",
                "Issuing Country",
                "The issuing country of the vehicle",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "GB".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "issue_date",
                "Issuing Date",
                "The date the vehicle was issued",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "2023-01-15T10:00:00-07:00".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "expiry_date",
                "Expiry Date",
                "The date the vehicle expires",
                true,
                VRC_NAMESPACE2,
                Icon.PLACE,
                "2027-07-07T12:00:00-06:00".toDataItem()
            )
            .addMdocAttribute(
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