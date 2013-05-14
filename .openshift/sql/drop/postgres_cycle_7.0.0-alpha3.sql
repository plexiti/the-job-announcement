
    alter table cy_connector_attributes 
        drop constraint FK1B26DCB29F2FC2CE
;

    alter table cy_connector_cred 
        drop constraint FKB57C4CE9607EEC3B
;

    alter table cy_connector_cred 
        drop constraint FKB57C4CE995597D1B
;

    alter table cy_roundtrip 
        drop constraint FKCE1A2ABB87F7FB
;

    alter table cy_roundtrip 
        drop constraint FKCE1A2AB563C930
;

    drop table if exists cy_bpmn_diagram cascade
;

    drop table if exists cy_connector_attributes cascade
;

    drop table if exists cy_connector_config cascade
;

    drop table if exists cy_connector_cred cascade
;

    drop table if exists cy_roundtrip cascade
;

    drop table if exists cy_user cascade
;

    drop table if exists cy_id_table cascade
;
