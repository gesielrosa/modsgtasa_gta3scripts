SCRIPT_START
{
NOP
LVAR_INT car_model
LVAR_INT car
LVAR_INT scplayer
LVAR_FLOAT object_coord[3]
LVAR_FLOAT player_coord[3]
LVAR_FLOAT dest_coord[3]
LVAR_INT cam_loop

cam_loop = 0

main_loop:
WAIT 0

IF TEST_CHEAT initvideo
    cam_loop = 0

    // VERIFICA SE O ARQUIVO DE CONFIGURAÇÃO ESTÁ CORRETO
    IF READ_FLOAT_FROM_INI_FILE "modloader\dev_files\config.ini" "dest_coord" "x" dest_coord[0]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\config.ini" "dest_coord" "y" dest_coord[1]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\config.ini" "dest_coord" "z" dest_coord[2]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\config.ini" "object_coord" "x" object_coord[0]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\config.ini" "object_coord" "y" object_coord[1]
    AND READ_FLOAT_FROM_INI_FILE "modloader\dev_files\config.ini" "object_coord" "z" object_coord[2]
    AND READ_INT_FROM_INI_FILE "modloader\dev_files\config.ini" "car_model" "model" car_model
        IF IS_THIS_MODEL_A_CAR car_model

            // PEGA O PERSONAGEM
            GET_PLAYER_CHAR 0 scplayer

            // CARREGA O MODELO DO CARRO
            REQUEST_MODEL car_model
            WHILE NOT HAS_MODEL_LOADED car_model
                WAIT 0
            ENDWHILE

            // MUDA O CLIMA PARA "ENSOLARADO"
            FORCE_WEATHER_NOW 1

            // ALTERA A HORA
            SET_TIME_OF_DAY 12 00

            // CRIA O CARRO NA COORDENADA INICIAL
            CREATE_CAR car_model object_coord[0] object_coord[1] object_coord[2] car

            // CONFIGURA A COORD DO PERSONAGEM BASEADA NA DO CARRO
            player_coord[0] = object_coord[0]
            player_coord[1] = object_coord[1]
            player_coord[2] = object_coord[2]
            player_coord[0] += 4.0
            player_coord[1] += 4.0
            player_coord[2] += 0.5

            // MUDA A COORDENADA DO PERSONAGEM
            SET_CHAR_COORDINATES scplayer player_coord[0] player_coord[1] player_coord[2]

            // MANDA O PERSNAGEM DIRIGIR ATE O DESTINO
            TASK_CAR_DRIVE_TO_COORD scplayer car dest_coord[0] dest_coord[1] dest_coord[2] 20.0 0 0 0

            // AGUARDA O PERSONAGEM ENTRAR NO CARRO
            WHILE NOT IS_CHAR_IN_CAR scplayer car
                WAIT 0
            ENDWHILE

            // CONFIGURA O ESTILO DE PILOTAGEM
            SET_CAR_DRIVING_STYLE car 2

            // DESLIGA A RADIO
            SET_RADIO_CHANNEL 12

            cam_loop:
            WHILE cam_loop < 8
                WAIT 0

                IF cam_loop = 0
                    ATTACH_CAMERA_TO_VEHICLE car 0.0 -6.0 1.5 0.0 0.0 0.0 0.0 2 // ATRAS
                ENDIF
                IF cam_loop = 1
                    ATTACH_CAMERA_TO_VEHICLE car 4.0 -6.0 1.5 0.0 0.0 0.0 0.0 2 // ATRAS DIR
                ENDIF
                IF cam_loop = 2
                    ATTACH_CAMERA_TO_VEHICLE car 6.0 0.0 1.5 0.0 0.0 0.0 0.0 2 // LATERAL DIREITA
                ENDIF
                IF cam_loop = 3
                    ATTACH_CAMERA_TO_VEHICLE car 4.0 6.0 1.5 0.0 0.0 0.0 0.0 2 // FRENTE DIR
                ENDIF
                IF cam_loop = 4
                    ATTACH_CAMERA_TO_VEHICLE car 0.0 6.0 1.5 0.0 0.0 0.0 0.0 2 // FRENTE
                ENDIF
                IF cam_loop = 5
                    ATTACH_CAMERA_TO_VEHICLE car -4.0 6.0 1.5 0.0 0.0 0.0 0.0 2 // FRENTE ESQ
                ENDIF
                IF cam_loop = 6
                    ATTACH_CAMERA_TO_VEHICLE car -6.0 0.0 1.5 0.0 0.0 0.0 0.0 2 // LATERAL ESQUERDA
                ENDIF
                IF cam_loop = 7
                    ATTACH_CAMERA_TO_VEHICLE car -4.0 -6.0 1.5 0.0 0.0 0.0 0.0 2 // ATRAS ESQ
                ENDIF

                WAIT 8000
                cam_loop += 1

                IF cam_loop = 8
                    cam_loop = 0
                ENDIF

                // QUANDO O CARRO ESTACIONAR ENCERRA TUDO
                IF IS_CAR_STOPPED car
                    cam_loop = 8
                    RESTORE_CAMERA
                ENDIF
            ENDWHILE

            MARK_CAR_AS_NO_LONGER_NEEDED car

        ENDIF
    ENDIF
    
ENDIF
GOTO main_loop
}
SCRIPT_END
