package tv.culturesport.sirrendergui;

/*
 * Copyright (c) 2016, Dale Kubler. All rights reserved.
 *
 */

public class ApplicationConstants {

	public static final String DEFAULT_SIRRENDER_LAN_DRIVE = "V";
	public static final String AWAITING_YOUR_COMMAND = "Awaiting your command...";
	public static final String DATABASE_UPDATE_OK = "DATABASE_UPDATE_OK";
	public static final String OVERRIDE_OUTPUT_DIR = " (Override internal Blender output path)";
	public static final String OVERRIDE_RENDER_DEVICE_C = " [CPU]";
	public static final String OVERRIDE_RENDER_DEVICE_G = " [GPU]";
	public static final String RENDER = "RENDER";
	public static final String RENDER_CMD_FILE = "RENDER_CMD_FILE";
	public static final String RENDER_FILE = "RENDER_FILE";
	public static final String FILENAME = "Filename?";
	public static final String SIRRENDER_SERVER_TITLE = "SirRender Server - Version 3.0";
	public static final String SIRRENDER_SERVER_CONSOLE_TITLE = "SirRender Server Console - Version 3.0";
	public static final String SIRRENDER_CLIENT_TITLE = "SirRender Client - Version 3.0\r\n\r\n";
	public static final String SIRRENDER_CLIENTBATCH_TITLE = "SirRender ClientBatch - Version 3.0\r\n\r\n";
	public static final String SIRRENDER_MONITOR_TITLE = "SirRender Monitor - Version 2.0\r\n\r\n";
	public static final String SIRRENDER_SERVER_ANNOUNCE = " reporting for duty\r\n(Ctrl-C to exit server)\n\r\n";
	public static final String NO_IO_FOR_CONNECTION = "Couldn't get I/O for the connection to ";
	public static final String DONT_KNOW_ABOUT_HOST = "Don't know about host ";
	public static final String BYE = "Bye.";
	public static final String CLIENT = "Client: ";
	public static final String CLIENTBATCH = "ClientBatch: ";
	public static final String SERVER = "Server: ";
	public static final String SERVER_INDENT = "        ";
	public static final String COULD_NOT_LISTEN_ON_PORT = "Could not listen on port ";
	public static final String CLIENT_CONNECT_CLOSED = "Client connect closed";
	public static final String VALID_COMMANDS = "Valid commands: help, render, quit";
	public static final String TRY_AGAIN = "Try again.";
	public static final String DEFAULT_PATH = DEFAULT_SIRRENDER_LAN_DRIVE+":\\SirRender2\\";
	public static final String DEFAULT_TMP_PATH = DEFAULT_SIRRENDER_LAN_DRIVE+":\\SirRender2\\temp\\";
	public static final String DEFAULT_OUTPUT_PATH = DEFAULT_SIRRENDER_LAN_DRIVE+":\\SirRender2\\tmpBlenderOut\\";
	public static final String DEFAULT_INPUT_PATH = DEFAULT_SIRRENDER_LAN_DRIVE+":\\SirRender2\\tmpBlenderIn\\";
	public static final String DEFAULT_SERVERLOG_CSV_DIRECTORY = DEFAULT_SIRRENDER_LAN_DRIVE+":\\";
	public static final String ENV_SIRRENDER_IP_ADDRESS = "SIRRENDER_IP_ADDRESS";
	public static final String ENV_SIRRENDER_TMP_PATH = "SIRRENDER_TMP_PATH";
	public static final String ENV_SIRRENDER_BLENDER_INPUT_PATH = "SIRRENDER_BLENDER_INPUT_PATH";
	public static final String ENV_SIRRENDER_BLENDER_OUTPUT_PATH = "SIRRENDER_BLENDER_OUTPUT_PATH";
	public static final String END = "END";
	public static final String TMP_FILE_PREFIX = "SirRender2";
	public static final String TMP_FILE_EXTENSION = ".bat";
	public static final String RENDER_PROCESS_STARTED_FOR = "Render process started for ";
	public static final String RENDER_PROCESS_IN_PROGRESS_FOR = "Render process running for ";
	public static final String RENDER_PROCESS_STARTED_ON_SERVER = "Render process started on server ";
	public static final String NEW_LINE = "\r\n";
	public static final String DEFAULT_PORT_NUMBER = "4444";
	public static final String SERVER_STATUS = "SERVER_STATUS";
	public static final String SERVER_STATUS_OK = "SERVER_STATUS_OK";
	public static final String SERVER_GET_SERVERS = "SERVER_GET_SERVERS";
	public static final String SERVER_GET_SERVER_DATA = "SERVER_GET_SERVER_DATA";
	public static final String SERVER_DELETE_SERVER_DATA = "SERVER_DELETE_SERVER_DATA";
	public static final String SERVER_SAVE_SERVER_DATA = "SERVER_SAVE_SERVER_DATA";
	public static final String SERVER_GET_RENDERING_FILE_STATUS = "SERVER_GET_RENDERING_FILE_STATUS";
	public static final String SERVER_GET_RENDERING_FILE_COUNTS = "SERVER_GET_RENDERING_FILE_COUNTS";
	public static final String SERVER_SET_MASTER_SERVER_IP_ADDRESS = "SERVER_SET_MASTER_SERVER_IP_ADDRESS";
	public static final String RENDER_STATUS = "RENDER_STATUS";
	public static final String RENDER_STATUS_MSG = "RENDER_STATUS_MSG";
	public static final String RENDER_UPDATE_CURRENT_FRAME = "RENDER_UPDATE_CURRENT_FRAME";
	public static final String RENDER_UPDATE_SUSPEND_KILL_SWITCH = "RENDER_UPDATE_SUSPEND_KILL_SWITCH";
	public static final String RENDER_UPDATE_SUSPEND_SWITCH = "RENDER_UPDATE_SUSPEND_SWITCH";
	public static final String RENDER_GET_KILL_SWITCH = "RENDER_GET_KILL_SWITCH";
	public static final String RENDER_GET_SUSPEND_KILL_SWITCH = "RENDER_GET_SUSPEND_KILL_SWITCH";
	public static final String RENDER_GET_SUSPEND_SWITCH = "RENDER_GET_SUSPEND_SWITCH";
	public static final String RENDER_UPDATE_KILL_SWITCH = "RENDER_UPDATE_KILL_SWITCH";
	public static final String LOG_SERVERLOG_MESSAGE = "LOG_SERVERLOG_MESSAGE";
	public static final String CREATE_SERVERLOG_CSV_FILE = "CREATE_SERVERLOG_CSV_FILE";
	public static final String DEFAULT_SERVERLOG_CSV_FILE_NAME = "SirRender2LogFile.csv";
	public static final String CSV_FILE_EXTENSION = ".csv";
	public static final String LOG_GET_SERVERLOG_LIST = "LOG_GET_SERVERLOG_LIST";
	public static final String LOG_GET_SERVERLOG_SERVER_NAME = "LOG_GET_SERVERLOG_SERVER_NAME";
	public static final String LOG_GET_SERVERLOG_SERVER_LIST = "LOG_GET_SERVERLOG_SERVER_LIST";
	public static final String LOG_GET_SERVERLOG_IP_ADDRESS_LIST = "LOG_GET_SERVERLOG_IP_ADDRESS_LIST";
	public static final String LOG_GET_SERVERLOG_BLEND_FILE_LIST = "LOG_GET_SERVERLOG_BLEND_FILE_LIST";
	public static final String RENDER_UPDATE_SERVER_QUEUE_ERROR_COUNT = "RENDER_UPDATE_SERVER_QUEUE_ERROR_COUNT";
	public static final String RENDER_ADD_SERVER_QUEUE_FILE = "RENDER_ADD_SERVER_QUEUE_FILE";
	public static final String RENDER_DELETE_SERVER_QUEUE_FILE = "RENDER_DELETE_SERVER_QUEUE_FILE";
	public static final String RENDER_READ_SERVER_QUEUE = "RENDER_READ_SERVER_QUEUE";
	public static final String RENDER_UPDATE_SERVER_STATUS_CANCEL = "RENDER_UPDATE_SERVER_STATUS_CANCEL";
	public static final String RENDER_UPDATE_SERVER_STATUS_COMPLETE = "RENDER_UPDATE_SERVER_STATUS_COMPLETE";
	public static final String RENDER_UPDATE_SERVER_STATUS_START_STATS = "RENDER_UPDATE_SERVER_STATUS_START_STATS";
	public static final String RENDER_RESET_SERVER_STATUS_KILL_SWITCH = "RENDER_RESET_SERVER_STATUS_KILL_SWITCH";
	public static final String RENDER_SERVER_STATUS_SELECT_KILL_SWITCH = "RENDER_SERVER_STATUS_SELECT_KILL_SWITCH";
	public static final String RENDER_UPDATE_SERVER_STATUS_STATUS = "RENDER_UPDATE_SERVER_STATUS_STATUS";
	public static final String RENDER_GET_SCHEDULE_IP_AVAILABLE_LIST = "RENDER_GET_SCHEDULE_IP_AVAILABLE_LIST";
	public static final String RENDER_GET_SCHEDULE_IP_FILE_AVAILABLE_LIST = "RENDER_GET_SCHEDULE_IP_FILE_AVAILABLE_LIST";
	public static final String RENDER_POLL_SCHEDULE_QUEUE = "RENDER_POLL_SCHEDULE_QUEUE";
	public static final String RENDER_GET_COMPUTER_IP_ALL_FILES_LIST = "RENDER_GET_COMPUTER_IP_ALL_FILES_LIST";
	public static final String GET_MASTER_SERVER_IP_ADDRESS = "GET_MASTER_SERVER_IP_ADDRESS";
	public static final String MASTER_SERVER_IP_ADDRESS_TXT_FILE = "/SirRender2/databases/masterServerIpAddress.txt";
	public static final String PURGE_STALE_SERVERS = "PURGE_STALE_SERVERS";
	public static final String INSERT_DUMMY_SERVER_QUEUE_FILE = "INSERT_DUMMY_SERVER_QUEUE_FILE";
	public static final int    DEFAULT_REFRESH_SECONDS = 30;
	
}
