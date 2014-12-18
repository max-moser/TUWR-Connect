package peak.can.basic;

/**
 * Represents a PCAN filter mode.
 *
 * @version 1.8
 * @LastChange 11/03/2013
 * @author Jonathan Urban/Uwe Wilhelm
 *
 * @Copyright (C) 1999-2013  PEAK-System Technik GmbH, Darmstadt
 * more Info at http://www.peak-system.com
 */
public enum TPCANMode
{
    /**Mode is Standard (11-bit identifier). */
    PCAN_MODE_STANDARD((byte)0x00),
    /**Mode is Extended (29-bit identifier). */
    PCAN_MODE_EXTENDED((byte)0x02);

    private TPCANMode(byte value)
    {
        this.value = value;
    }

    public byte getValue()
    {
        return this.value;
    }
    private final byte value;
};
