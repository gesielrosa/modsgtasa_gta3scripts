SCRIPT_START
{
NOP
LVAR_FLOAT camera_coord[3]
LVAR_FLOAT object_coord[3]

main_loop:
WAIT 0
IF TEST_CHEAT setcamera
    IF READ_FLOAT_FROM_INI_FILE "modloader\dev_files\info.ini" "camera_coord" "x" camera_coord[0]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\info.ini" "camera_coord" "y" camera_coord[1]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\info.ini" "camera_coord" "z" camera_coord[2]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\info.ini" "object_coord" "x" object_coord[0]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\info.ini" "object_coord" "y" object_coord[1]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\info.ini" "object_coord" "z" object_coord[2]
        SET_FIXED_CAMERA_POSITION camera_coord[0] camera_coord[1] camera_coord[2] 0.0 0.0 0.0
        POINT_CAMERA_AT_POINT object_coord[0] object_coord[1] object_coord[2] 1
        PRINT_FORMATTED "SET_CAMERA_POSITION" 2000
    ENDIF
ENDIF
GOTO main_loop
}
SCRIPT_END
