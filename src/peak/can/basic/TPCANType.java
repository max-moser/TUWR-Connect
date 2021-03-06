package peak.can.basic;
/**
 * Represents a PCAN device
 *
 * @version 1.6
 * @LastChange 13/01/2011
 * @author Jonathan Urban/Uwe Wilhelm
 *
 * @Copyright (C) 1999-2012  PEAK-System Technik GmbH, Darmstadt
 * more Info at http://www.peak-system.com
 */
public enum TPCANType
{
    /**Undefined, unknown or not selected PCAN type value*/
    PCAN_TYPE_NONE((byte)0x00),
    /**PCAN-ISA 82C200*/
    PCAN_TYPE_ISA((byte)0x01),
    /**PCAN-ISA SJA1000*/
    PCAN_TYPE_ISA_SJA((byte)0x09),
    /**PHYTEC ISA*/
    PCAN_TYPE_ISA_PHYTEC((byte)0x04),
    /**PCAN-Dongle 82C200*/
    PCAN_TYPE_DNG((byte)0x02),
    /**PCAN-Dongle EPP 82C200*/
    PCAN_TYPE_DNG_EPP((byte)0x03),
    /**PCAN-Dongle SJA1000*/
    PCAN_TYPE_DNG_SJA((byte)0x05),
    /**PCAN-Dongle EPP SJA1000*/
    PCAN_TYPE_DNG_SJA_EPP((byte)0x06);

    private TPCANType(byte value)
    {
        this.value = value;
    }

    public byte getValue()
    {
        return this.value;
    }
    private final byte value;
};
