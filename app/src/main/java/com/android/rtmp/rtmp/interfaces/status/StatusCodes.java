package com.android.rtmp.rtmp.interfaces.status;

/*
 * Created by Bo on 23.08.2015.
 */
/**
 * Collection of commonly used constants with status codes. Descriptions provided as in FMS 2.0.1 
 * documentation available at adobe.com with some minor additions and comments.
 */
public interface StatusCodes {
    /**
     * The NetConnection.call method was not able to invoke the server-side method or
     * command.
     */
     String NC_CALL_FAILED = "NetConnection.Call.Failed";
    /**
     * The URI specified in the NetConnection.connect method did not
     * specify 'rtmp' as the protocol. 'rtmp' must be specified when connecting to
     * FMS and Red5. Either not supported version of AMF was used (3 when only 0 is supported)
     */
     String NC_CALL_BADVERSION = "NetConnection.Call.BadVersion";
    /**
     * The application has been shut down (for example, if the application is out of
     * memory resources and must shut down to prevent the server from crashing) or the server has shut down.
     */
     String NC_CONNECT_APPSHUTDOWN = "NetConnection.Connect.AppShutdown";
    /**
     * The connection was closed successfully
     */
     String NC_CONNECT_CLOSED = "NetConnection.Connect.Closed";
    /**
     * The connection attempt failed.
     */
     String NC_CONNECT_FAILED = "NetConnection.Connect.Failed";
    /**
     * The client does not have permission to connect to the application, the
     * application expected different parameters from those that were passed,
     * or the application name specified during the connection attempt was not found on
     * the server.
     */
     String NC_CONNECT_REJECTED = "NetConnection.Connect.Rejected";
    /**
     * The connection attempt succeeded.
     */
     String NC_CONNECT_SUCCESS = "NetConnection.Connect.Success";
    /**
     * The application name specified during connect is invalid.
     */
     String NC_CONNECT_INVALID_APPLICATION = "NetConnection.Connect.InvalidApp";
    /**
     * Invalid arguments were passed to a NetStream method.
     */
     String NS_INVALID_ARGUMENT = "NetStream.InvalidArg";
    /**
     * A recorded stream was deleted successfully.
     */
     String NS_CLEAR_SUCCESS = "NetStream.Clear.Success";
    /**
     * A recorded stream failed to delete.
     */
     String NS_CLEAR_FAILED = "NetStream.Clear.Failed";
    /**
     * An attempt to publish was successful.
     */
     String NS_PUBLISH_START = "NetStream.Publish.Start";
    /**
     * An attempt was made to publish a stream that is already being published by someone else.
     */
     String NS_PUBLISH_BADNAME = "NetStream.Publish.BadName";
    /**
     * An attempt to use a Stream method (at client-side) failed
     */
     String NS_FAILED = "NetStream.Failed";
    /**
     * An attempt to unpublish was successful
     */
     String NS_UNPUBLISHED_SUCCESS = "NetStream.Unpublish.Success";
    /**
     * Recording was started
     */
     String NS_RECORD_START = "NetStream.Record.Start";
    /**
     * An attempt was made to record a read-only stream
     */
     String NS_RECORD_NOACCESS = "NetStream.Record.NoAccess";
    /**
     * Recording was stopped
     */
     String NS_RECORD_STOP = "NetStream.Record.Stop";
    /**
     * An attempt to record a stream failed
     */
     String NS_RECORD_FAILED = "NetStream.Record.Failed";

    /**
     * The buffer is empty (sent from server to client)
     */
     String NS_BUFFER_EMPTY = "NetStream.Buffer.Empty";

    /**
     * Data is playing behind the normal speed
     */
     String NS_PLAY_INSUFFICIENT_BW = "NetStream.Play.InsufficientBW";
    /**
     * Play was started
     */
     String NS_PLAY_START = "NetStream.Play.Start";
    /**
     * An attempt was made to play a stream that does not exist
     */
     String NS_PLAY_STREAMNOTFOUND = "NetStream.Play.StreamNotFound";
    /**
     * Play was stopped
     */
     String NS_PLAY_STOP = "NetStream.Play.Stop";
    /**
     * An attempt to play back a stream failed
     */
     String NS_PLAY_FAILED = "NetStream.Play.Failed";
    /**
     * A playlist was reset
     */
     String NS_PLAY_RESET = "NetStream.Play.Reset";
    /**
     * The initial publish to a stream was successful. This message is sent to all subscribers
     */
     String NS_PLAY_PUBLISHNOTIFY = "NetStream.Play.PublishNotify";
    /**
     * An unpublish from a stream was successful. This message is sent to all subscribers
     */
     String NS_PLAY_UNPUBLISHNOTIFY = "NetStream.Play.UnpublishNotify";
    /**
     * Playlist playback switched from one stream to another.
     */
     String NS_PLAY_SWITCH = "NetStream.Play.Switch";
    /**
     * Playlist playback is complete.
     */
     String NS_PLAY_COMPLETE = "NetStream.Play.Complete";
    /**
     * The subscriber has used the seek command to move to a particular location in the recorded stream.
     */
     String NS_SEEK_NOTIFY = "NetStream.Seek.Notify";
    /**
     * The stream doesn't support seeking.
     */
     String NS_SEEK_FAILED = "NetStream.Seek.Failed";
    /**
     * The subscriber has used the seek command to move to a particular location in the recorded stream.
     */
     String NS_PAUSE_NOTIFY = "NetStream.Pause.Notify";
    /**
     * Publishing has stopped
     */
     String NS_UNPAUSE_NOTIFY = "NetStream.Unpause.Notify";
    /**
     *
     */
     String NS_DATA_START = "NetStream.Data.Start";
    /**
     * The ActionScript engine has encountered a runtime error. In addition to the standard infoObject
     * properties, the following properties are set:
     *
     * filename: name of the offending ASC file.
     * lineno: line number where the error occurred.
     * linebuf: source code of the offending line.
     */
     String APP_SCRIPT_ERROR = "Application.Script.Error";
    /**
     * The ActionScript engine has encountered a runtime warning. In addition to the standard infoObject
     * properties, the following properties are set:
     *
     * filename: name of the offending ASC file.
     * lineno: line number where the error occurred.
     * linebuf: source code of the offending line
     */
     String APP_SCRIPT_WARNING = "Application.Script.Warning";
    /**
     * The ActionScript engine is low on runtime memory. This provides an opportunity for the
     * application instance to free some resources or take suitable action. If the application instance
     * runs out of memory, it is unloaded and all users are disconnected. In this state, the server will
     * not invoke the Application.onDisconnect event handler or the Application.onAppStop event handler
     */
     String APP_RESOURCE_LOWMEMORY = "Application.Resource.LowMemory";
    /**
     * This information object is passed to the onAppStop handler when the application is being shut down
     */
     String APP_SHUTDOWN = "Application.Shutdown";
    /**
     * This information object is passed to the onAppStop event handler when the application instance
     * is about to be destroyed by the server.
     */
     String APP_GC = "Application.GC";
    /**
     * Read access to a shared object was denied.
     */
     String SO_NO_READ_ACCESS = "SharedObject.NoReadAccess";
    /**
     * Write access to a shared object was denied.
     */
     String SO_NO_WRITE_ACCESS = "SharedObject.NoWriteAccess";
    /**
     * The creation of a shared object was denied.
     */
     String SO_CREATION_FAILED = "SharedObject.ObjectCreationFailed";
    /**
     * The persistence parameter passed to SharedObject.getRemote() is different from the one used
     * when the shared object was created.
     */
     String SO_PERSISTENCE_MISMATCH = "SharedObject.BadPersistence";

    /**
     * This event is sent if the player detects an MP4 with an invalid file structure. 
     * Flash Player cannot play files that have invalid file structures.
     *
     * New for FMS3
     */
     String NS_PLAY_FILE_STRUCTURE_INVALID = "NetStream.Play.FileStructureInvalid";

    /**
     * This event is sent if the player does not detect any supported tracks. If there aren't any supported
     * video, audio or data tracks found, Flash Player does not play the file.
     *
     * New for FMS3
     */
     String NS_PLAY_NO_SUPPORTED_TRACK_FOUND = "NetStream.Play.NoSupportedTrackFound";


}