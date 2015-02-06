package peak.can.basic;

import java.util.Stack;
import java.util.Vector;

/**
 * Parameter definition.
 *
 * @version 1.8.1
 * @LastChange 11/03/2013
 * @author Jonathan Urban/Uwe Wilhelm
 *
 * @Copyright (C) 1999-2013  PEAK-System Technik GmbH, Darmstadt
 * more Info at http://www.peak-system.com
 */
public enum TPCANParameter
{
    /**PCAN-USB device number parameter*/
    PCAN_DEVICE_NUMBER(1),
    /**PCAN-PC Card 5-Volt power parameter*/
    PCAN_5VOLTS_POWER(2),
    /**PCAN receive event handler parameter*/
    PCAN_RECEIVE_EVENT(3),
    /**PCAN message filter parameter*/
    PCAN_MESSAGE_FILTER(4),
    /**PCAN-Basic API version parameter*/
    PCAN_API_VERSION(5),
    /**PCAN device channel version parameter*/
    PCAN_CHANNEL_VERSION(6),
    /**PCAN Reset-On-Busoff parameter*/
    PCAN_BUSOFF_AUTORESET(7),
    /**PCAN Listen-Only parameter*/
    PCAN_LISTEN_ONLY(8),
    /**Directory path for trace files*/
    PCAN_LOG_LOCATION(9),
    /**Debug-Trace activation status*/
    PCAN_LOG_STATUS(10),
    /**Configuration of the debugged information (LOG_FUNCTION_***)*/
    PCAN_LOG_CONFIGURE(11),
    /**Custom insertion of text into the log file*/
    PCAN_LOG_TEXT(12),
    /**Availability status of a PCAN-Channel*/
    PCAN_CHANNEL_CONDITION(13),
    /**PCAN hardware name parameter*/
    PCAN_HARDWARE_NAME(14),
    /**Message reception status of a PCAN-Channel*/
    PCAN_RECEIVE_STATUS(15),
    /**CAN-Controller number of a PCAN-Channel*/
    PCAN_CONTROLLER_NUMBER(16),
    /**Directory path for PCAN Trace files*/
    PCAN_TRACE_LOCATION(17),
    /**Tracing activation status*/
    PCAN_TRACE_STATUS(18),
    /**Configuration of the maximum file size of a CAN trace*/
    PCAN_TRACE_SIZE(19),
    /**Configuration of the trace file storing mode (TRACE_FILE_***)*/
    PCAN_TRACE_CONFIGURE(20);  


    private TPCANParameter(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    /**
     * Returns the list of PCAN Parameters which are customizable as array
     * @return the list of PCAN Parameters which are customizable as array
     */
    public static Object[] customizableParameters()
    {
        Vector<TPCANParameter> result = new Stack<TPCANParameter>();
        result.add(TPCANParameter.PCAN_DEVICE_NUMBER);
        result.add(TPCANParameter.PCAN_5VOLTS_POWER);
        result.add(TPCANParameter.PCAN_API_VERSION);
        result.add(TPCANParameter.PCAN_CHANNEL_VERSION);
        result.add(TPCANParameter.PCAN_BUSOFF_AUTORESET);
        result.add(TPCANParameter.PCAN_LISTEN_ONLY);
        result.add(TPCANParameter.PCAN_LOG_LOCATION);
        result.add(TPCANParameter.PCAN_LOG_STATUS);
        result.add(TPCANParameter.PCAN_LOG_CONFIGURE);
        result.add(TPCANParameter.PCAN_CHANNEL_CONDITION);
        result.add(TPCANParameter.PCAN_HARDWARE_NAME);
        result.add(TPCANParameter.PCAN_RECEIVE_STATUS);
        result.add(TPCANParameter.PCAN_CONTROLLER_NUMBER);
        result.add(TPCANParameter.PCAN_TRACE_LOCATION);
        result.add(TPCANParameter.PCAN_TRACE_STATUS);
        result.add(TPCANParameter.PCAN_TRACE_SIZE);
        result.add(TPCANParameter.PCAN_TRACE_CONFIGURE);
        return result.toArray();
    }

    /**
     * Returns a description for the given TPCANParameter
     * @param param TPCANParameter for which we need description
     * @return
     */
    public static String getParameterDescription(TPCANParameter param)
    {
        String description = "";
        switch (param)
        {
            case PCAN_DEVICE_NUMBER:
                description = "This parameter is used on PCAN-USB hardware to distinguish between 2 (or more) of them on the same computer. This value is persistent, i.e. the identifier will not be lost after disconnecting and connecting again the hardware.";
                break;
            case PCAN_5VOLTS_POWER:
                description = "This parameter is used on PCAN-PC Card hardware for switching the external 5V on the D-Sub connector of the PC Card. This is useful when connecting external bus converter modules to the card (AU5790 / TJA1954)).";
                break;
            case PCAN_API_VERSION:
                description = "This parameter is used to get information about the PCAN-Basic API implementation version.";
                break;
            case PCAN_CHANNEL_VERSION:
                description = "This parameter is used to get version information about the Driver of a PCAN Channel.";
                break;
            case PCAN_BUSOFF_AUTORESET:
                description = "This parameter instructs the PCAN driver to reset automatically the CAN controller of a PCAN channel when a bus-off state is detected. Since no communication is possible on a bus-off state, it is useful to let the driver to catch this event automatically and reset the controller, avoiding extra handling of this problem in an end application.";
                break;
            case PCAN_LISTEN_ONLY:
                description = "This parameter allows the user to set a CAN hardware in Listen-Only mode. When this mode is set, the CAN controller doens't take part on active events (eg. transmit CAN messages) but stays in a passive mode (CAN monitor), in which it can analyse the traffic on the CAN bus used by a PCAN channel. See also the Philips Data Sheet \"SJA1000 Stand-alone CAN controller\".";
                break;
            case PCAN_LOG_LOCATION:
                description = "This value is used to set the folder location on a computer for the Log-File generated by the PCAN-Basic API, within a debug session. Setting this value starts recording debug information automatically. If a debug session is running (a log file is being written), PCAN_LOG_LOCATION instructs the API to close the current log file and to start the process again with the new folder information. Note that the name of the log file cannot be specified, this name is fixed as PCANBasic.log.";
                break;
            case PCAN_LOG_STATUS:
                description = "This value is used to control the activity status of a debug session within the PCAN-Basic API. If the log status is set to ON without having set a location for the log file or without having configured the information to be traced, then the session process will start with the default values.";
                break;
            case PCAN_LOG_CONFIGURE:
                description = "This value is used to configure the debug information to be included in the log file generated in a debug session within the PCAN-Basic API.";
                break;
            case PCAN_CHANNEL_CONDITION:
                description = "This parameter is used to check and detect available PCAN hardware on a computer, even before trying to connect any of them. This is useful when an application wants the user to select which hardware should be using in a communication session.";
                break;
            case PCAN_HARDWARE_NAME:
                description = "This parameter is to read the Name of the Hardware.";
                break;                
            case PCAN_RECEIVE_STATUS:
                description = "This Parameter is to get the reception status of a PCAN-Channel.";
                break;      
            case PCAN_CONTROLLER_NUMBER:
                description = "This Parameter is to get CAN-Controller number of a PCAN-Channel. Only usefull with 2 Channel HW.";
                break;      
            case PCAN_TRACE_LOCATION:
                description = "This Parameter is used to configure the Trace File Directory.";
                break;
            case PCAN_TRACE_STATUS:
                description = "This Parameter is used to control the activity of the Tracer status.";
                break;
            case PCAN_TRACE_SIZE:
                description = "This Parameter is used to configure the maximum file size of a CAN trace.";
                break;                
            case PCAN_TRACE_CONFIGURE:
                description = "This Parameter is used to configure the trace file storing mode (TRACE_FILE_***).";
                break;
			default:
				break;                                    
        }
        return description;
    }
    private final int value;
}
