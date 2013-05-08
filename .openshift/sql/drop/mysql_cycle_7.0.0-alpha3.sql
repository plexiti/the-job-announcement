
    alter table cy_connector_attributes 
        drop 
        foreign key FK1B26DCB29F2FC2CE
;

    alter table cy_connector_cred 
        drop 
        foreign key FKB57C4CE9607EEC3B
;

    alter table cy_connector_cred 
        drop 
        foreign key FKB57C4CE995597D1B
;

    alter table cy_roundtrip 
        drop 
        foreign key FKCE1A2ABB87F7FB
;

    alter table cy_roundtrip 
        drop 
        foreign key FKCE1A2AB563C930
;

    drop table if exists cy_bpmn_diagram
;

    drop table if exists cy_connector_attributes
;

    drop table if exists cy_connector_config
;

    drop table if exists cy_connector_cred
;

    drop table if exists cy_roundtrip
;

    drop table if exists cy_user
;

    drop table if exists cy_id_table
;
