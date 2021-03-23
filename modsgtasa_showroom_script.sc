SCRIPT_START
{
NOP
LVAR_FLOAT camera_coord[3]
LVAR_FLOAT object_coord[3]
LVAR_FLOAT object_initial_angle
LVAR_FLOAT player_coord[3]
LVAR_INT car_model
LVAR_INT car
LVAR_INT loop
LVAR_INT scplayer

object_initial_angle = 215.0
loop = 0

main_loop:
WAIT 0

IF TEST_CHEAT modsphoto
    loop = 0

    // VERIFICA SE O ARQUIVO DE CONFIGURAÇÃO ESTÁ CORRETO
    IF READ_FLOAT_FROM_INI_FILE "CLEO\modsgtasa_config.ini" "camera_coord" "x" camera_coord[0]
    AND READ_FLOAT_FROM_INI_FILE "CLEO\modsgtasa_config.ini" "camera_coord" "y" camera_coord[1]
    AND READ_FLOAT_FROM_INI_FILE "CLEO\modsgtasa_config.ini" "camera_coord" "z" camera_coord[2]
    AND READ_FLOAT_FROM_INI_FILE "CLEO\modsgtasa_config.ini" "object_coord" "x" object_coord[0]
    AND READ_FLOAT_FROM_INI_FILE "CLEO\modsgtasa_config.ini" "object_coord" "y" object_coord[1]
    AND READ_FLOAT_FROM_INI_FILE "CLEO\modsgtasa_configs.ini" "object_coord" "z" object_coord[2]
    AND READ_INT_FROM_INI_FILE "CLEO\modsgtasa_config.ini" "car_model" "model" car_model
        IF IS_THIS_MODEL_A_CAR car_model

            PRINT_FORMATTED "MODS GTA SAN ANDREAS" 1000

            // DEFINE A POS DO PLAYER BASEADO NA POS DA CAMERA
            player_coord[0] = camera_coord[0]
            player_coord[1] = camera_coord[1]
            player_coord[2] = camera_coord[2]
            player_coord[0] += 1.0
            player_coord[1] += 1.0

            // REQUEST MODEL
            REQUEST_MODEL car_model
            WHILE NOT HAS_MODEL_LOADED car_model
                WAIT 0
            ENDWHILE

            // MUDA O CLIMA PARA "ENSOLARADO"
            FORCE_WEATHER_NOW 1

            // ALTERA A HORA
            SET_TIME_OF_DAY 12 00

            // COLOCA O PLAYER PROXIMO DO LOCAL (PARA CARREGAR O AMBIENTE PROXIMO)
            GET_PLAYER_CHAR 0 scplayer
            SET_CHAR_COORDINATES scplayer player_coord[0] player_coord[1] player_coord[2]

            // POSITION CAMERA
            SET_FIXED_CAMERA_POSITION camera_coord[0] camera_coord[1] camera_coord[2] 0.0 0.0 0.0
            POINT_CAMERA_AT_POINT object_coord[0] object_coord[1] object_coord[2] 2

            // COLOCA A COORDENADA ACIMA DO SOLO (EIXO Z)
            object_coord[2] += 1.0

            // CRIA O CARRO
            CREATE_CAR car_model object_coord[0] object_coord[1] object_coord[2] car

            WHILE loop < 8
                WAIT 0

                // ROTACIONA O CARRO
                SET_CAR_HEADING car object_initial_angle

                // FAZ COM QUE O CARRO SEJA "TOCADO" E CAIA NO CHÃO (EVITA FICAR FLUTUANDO)
                SET_CAR_HYDRAULICS car 1
                SET_CAR_HYDRAULICS car 0

                GOSUB take_photo
                object_initial_angle += 45.0
                loop += 1
            ENDWHILE

            DELETE_CAR car
            MARK_VEHICLE_MOD_AS_NO_LONGER_NEEDED car

            GOSUB restore_camera

        ENDIF
    ENDIF
    
ENDIF
GOTO main_loop
}
SCRIPT_END

{
    // TIRA UM PRINT SCREEEN
    take_photo:
    WAIT 2000
    TAKE_PHOTO 1
    WAIT 1000
    RETURN
}

{
    // RESTAURA A CAMERA AO MODO PADRAO
    restore_camera:
    CAMERA_PERSIST_POS 0
    RESTORE_CAMERA
    RETURN
}
