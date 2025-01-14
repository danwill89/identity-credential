package com.android.identity.wallet.documentdata

import com.android.identity.documenttype.DocumentAttributeType
import com.android.identity.documenttype.StringOption

object DVLAVehicleRegistration {
    const val VRC_NAMESPACE = "org.iso.7367.1"
    const val VRC_DOCTYPE = "org.iso.7367.1.mVRC"
    fun getMdocComplexTypes() = MdocComplexTypes.Builder(VRC_DOCTYPE)
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("vehicle_holder"),
            true,
            "given_name_unicode",
            "Given Name",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("vehicle_holder"),
            true,
            "family_name_unicode",
            "Family Name",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("vehicle_holder"),
            true,
            "resident_address",
            "Resident Address",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("vehicle_holder"),
            true,
            "resident_city",
            "Resident City",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("vehicle_holder"),
            true,
            "resident_country",
            "Resident Country",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("vehicle_holder"),
            true,
            "resident_postal_code",
            "Resident Postal Code",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("basic_vehicle_info"),
            true,
            "vehicle_category_code",
            "Vehicle Category Code",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("basic_vehicle_info"),
            true,
            "type_approval_number",
            "Type Approval Number",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("basic_vehicle_info"),
            true,
            "make",
            "Make",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("basic_vehicle_info"),
            true,
            "commercial_name",
            "Commercial Name",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("basic_vehicle_info"),
            true,
            "colours",
            "Colours",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("mass_info"),
            true,
            "unit",
            "Unit",
            DocumentAttributeType.String
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("mass_info"),
            true,
            "techn_perm_max_laden_mass",
            "Techn Perm Max Laden Mass",
            DocumentAttributeType.Number
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("mass_info"),
            true,
            "vehicle_max_mass",
            "Vehicle Max Mass",
            DocumentAttributeType.Number
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("mass_info"),
            true,
            "whole_vehicle_max_mass",
            "Whole Vehicle Max Mass",
            DocumentAttributeType.Number
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("mass_info"),
            true,
            "mass_in_running_order",
            "Mass In Running Order",
            DocumentAttributeType.Number
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("engine_info"),
            true,
            "engine_capacity",
            "Engine Capacity",
            DocumentAttributeType.Number
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("engine_info"),
            true,
            "engine_power",
            "Engine Power",
            DocumentAttributeType.Number
        )
        .addDefinition(
            VRC_NAMESPACE,
            hashSetOf("engine_info"),
            true,
            "energy_source",
            "Energy Source",
            DocumentAttributeType.String
        )
        .build()
}