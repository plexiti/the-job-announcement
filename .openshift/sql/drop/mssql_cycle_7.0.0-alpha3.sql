
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

    drop table cy_bpmn_diagram
;

    drop table cy_connector_attributes
;

    drop table cy_connector_config
;

    drop table cy_connector_cred
;

    drop table cy_roundtrip
;

    drop table cy_user
;

    drop table cy_id_table
;
