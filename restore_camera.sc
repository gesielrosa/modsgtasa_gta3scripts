SCRIPT_START
{
NOP

main_loop:
WAIT 0
IF TEST_CHEAT restorecamera
    CAMERA_PERSIST_POS 0
    RESTORE_CAMERA
    PRINT_FORMATTED "RESTORE_CAMERA" 2000
ENDIF
GOTO main_loop
}
SCRIPT_END