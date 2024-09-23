CREATE DATABASE baseDatos;

CREATE TABLE EVAL_TIP_GESTION (
    n_id_tipo_gestion INT PRIMARY KEY,
    C_DESCRIPCION VARCHAR(255),
    N_PESO INT,
    N_ESTADO INT,
    C_USU_REGISTRO VARCHAR(50),
    D_FEC_REGISTRO DATETIME
);


INSERT INTO EVAL_TIP_GESTION (n_id_tipo_gestion, C_DESCRIPCION, N_PESO, N_ESTADO, C_USU_REGISTRO, D_FEC_REGISTRO)
VALUES
(3, 'Gestion visa', 60, 1, 'GNOAIN', '2018-11-19 21:19:00'),
(4, 'Gestion MC', 50, 1, 'GNOAIN', '2018-11-19 21:19:00'),
(5, 'Contracargo Visa', 0, 1, 'GNOAIN', '2018-11-19 21:19:00'),
(6, 'Contracargo MC', 0, 1, 'GNOAIN', '2018-11-19 21:19:00');


CREATE TABLE EVAL_REQ_DETALLE (
    n_id_req_detalle INT PRIMARY KEY,
    NIID_RQ DECIMAL(15, 2),
    C_COD_AUTORIZACION VARCHAR(50),
    D_FEC_TRX DATETIME,
    N_MTO DECIMAL(15, 2),
    C_USU_REGISTRO VARCHAR(50),
    D_FEC_REGISTRO DATETIME,
    N_ID_TIPO_GESTION INT,
    D_FEC_GESTION DATETIME
);


INSERT INTO EVAL_REQ_DETALLE (n_id_req_detalle, NIID_RQ, C_COD_AUTORIZACION, D_FEC_TRX, N_MTO, C_USU_REGISTRO, D_FEC_REGISTRO, N_ID_TIPO_GESTION, D_FEC_GESTION)
VALUES
(1000, 2793862.00, '080342', '2024-05-29 00:00:00', 14.90, 'SISTEMA', '2024-06-06 01:20:51', 5, '2024-06-10 16:50:16'),
(1001, 2793866.00, '000838', '2023-11-13 00:00:00', 14.90, 'SISTEMA', '2024-06-06 01:21:05', 5, '2024-06-10 16:50:16'),
(1002, 2793890.00, 'T05657', '2024-05-07 00:00:00', 15.01, 'SISTEMA', '2024-06-06 01:21:52', 5, '2024-06-10 16:50:16'),
(1003, 2793890.00, 'T02270', '2024-05-18 00:00:00', 27.81, 'SISTEMA', '2024-06-06 01:21:55', 5, '2024-06-10 16:50:16'),
(1004, 2793890.00, 'T06869', '2024-04-20 00:00:00', 30.90, 'SISTEMA', '2024-06-06 01:21:56', 5, '2024-06-10 16:50:16'),
(1005, 2793890.00, 'T06010', '2024-05-15 00:00:00', 31.76, 'SISTEMA', '2024-06-06 01:21:50', 5, '2024-06-10 16:50:16'),
(1006, 2793842.00, 'T05540', '2024-05-01 00:00:00', 35.50, 'SISTEMA', '2024-06-06 01:15:12', 4, '2024-06-10 16:50:16'),
(1007, 2793842.00, 'T07955', '2024-04-27 00:00:00', 36.90, 'SISTEMA', '2024-06-06 01:15:37', 4, '2024-06-10 16:50:16'),
(1008, 2793901.00, '046177', '2024-05-31 00:00:00', 37.90, 'SISTEMA', '2024-06-06 01:22:18', 4, '2024-06-10 16:50:16'),
(1009, 2793898.00, '007072', '2024-05-28 00:00:00', 42.85, 'SISTEMA', '2024-06-06 01:22:10', 4, '2024-06-10 16:50:16'),
(1010, 2793873.00, 'T07128', '2024-04-13 00:00:00', 113.85, 'SISTEMA', '2024-06-06 01:21:18', 3, '2024-06-10 16:50:16'),
(1011, 2793849.00, '028185', '2024-04-20 00:00:00', 178.20, 'SISTEMA', '2024-06-06 01:18:19', 3, '2024-06-10 16:50:16'),
(1012, 2793891.00, 'T00330', '2024-02-22 00:00:00', 619.00, 'SISTEMA', '2024-06-06 01:21:59', 3, '2024-06-10 16:50:16'),
(1013, 2793878.00, '079822', '2024-06-03 00:00:00', 1856.96, 'SISTEMA', '2024-06-06 01:21:26', 3, '2024-06-10 16:50:16');


-- Crear el paquete
CREATE OR REPLACE PACKAGE PKG_EVAL_ASIGNACION AS
    PROCEDURE ASIGNAR_TIPO_GESTION;
END PKG_EVAL_ASIGNACION;

-- Crear la implementación del paquete
CREATE OR REPLACE PACKAGE BODY PKG_EVAL_ASIGNACION AS

    PROCEDURE ASIGNAR_TIPO_GESTION IS
        CURSOR cur_eval_req IS
            SELECT n_id_req_detalle, N_MTO
            FROM EVAL_REQ_DETALLE;

        v_n_id_tipo_gestion INT;
        v_d_fec_gestion DATE := SYSDATE; -- Fecha de hoy

    BEGIN
        FOR rec IN cur_eval_req LOOP
            -- Determinar el tipo de gestión basado en el monto
            IF rec.N_MTO <= 10 THEN
                v_n_id_tipo_gestion := 6;
            ELSIF rec.N_MTO > 10 AND rec.N_MTO <= 35 THEN
                v_n_id_tipo_gestion := 5;
            ELSIF rec.N_MTO > 35 AND rec.N_MTO <= 100 THEN
                v_n_id_tipo_gestion := 4;
            ELSIF rec.N_MTO > 100 THEN
                v_n_id_tipo_gestion := 3;
            ELSE
                v_n_id_tipo_gestion := NULL; -- En caso de que no cumpla con ningún criterio
            END IF;

            -- Actualizar la tabla si se determinó un tipo de gestión
            IF v_n_id_tipo_gestion IS NOT NULL THEN
                UPDATE EVAL_REQ_DETALLE
                SET n_id_tipo_gestion = v_n_id_tipo_gestion,
                    d_fec_gestion = v_d_fec_gestion
                WHERE n_id_req_detalle = rec.n_id_req_detalle;
            END IF;
        END LOOP;

        COMMIT; -- Hacer commit de los cambios realizados
    END ASIGNAR_TIPO_GESTION;

END PKG_EVAL_ASIGNACION;

