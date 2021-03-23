SCRIPT_START
{
NOP
LVAR_INT player
LVAR_FLOAT coord[3]

GET_PLAYER_CHAR 0 player

main_loop:
WAIT 0
IF TEST_CHEAT getcharcoord
    GET_CHAR_COORDINATES player coord[0] coord[1] coord[2]
    WRITE_FLOAT_TO_INI_FILE coord[0] "modloader\dev_files\info.ini" "object_coord" "x"
    WRITE_FLOAT_TO_INI_FILE coord[1] "modloader\dev_files\info.ini" "object_coord" "y"
    WRITE_FLOAT_TO_INI_FILE coord[2] "modloader\dev_files\info.ini" "object_coord" "z"
    PRINT_FORMATTED "GET_CHAR_COORDINATES" 2000
ENDIF
GOTO main_loop
}
SCRIPT_END
