SCRIPT_START
{
NOP
LVAR_FLOAT coord[3]

main_loop:
WAIT 0
IF TEST_CHEAT getcamera
    GET_ACTIVE_CAMERA_COORDINATES coord[0] coord[1] coord[2]
    WRITE_FLOAT_TO_INI_FILE coord[0] "modloader\dev_files\info.ini" "camera_coord" "x"
    WRITE_FLOAT_TO_INI_FILE coord[1] "modloader\dev_files\info.ini" "camera_coord" "y"
    WRITE_FLOAT_TO_INI_FILE coord[2] "modloader\dev_files\info.ini" "camera_coord" "z"
    PRINT_FORMATTED "GET_ACTIVE_CAMERA_COORDINATES" 2000
ENDIF
GOTO main_loop
}
SCRIPT_END
