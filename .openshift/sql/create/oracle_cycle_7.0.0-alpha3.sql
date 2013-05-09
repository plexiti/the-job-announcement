
    create table cy_bpmn_diagram (
        id number(19,0) not null,
        connectorId number(19,0),
        diagramPath varchar2(255 char),
        label varchar2(255 char),
        lastModified timestamp,
        lastSync timestamp,
        modeler varchar2(255 char),
        status varchar2(255 char),
        primary key (id)
    )
;

    create table cy_connector_attributes (
        configuration_id number(19,0) not null,
        value varchar2(255 char),
        name varchar2(255 char) not null,
        primary key (configuration_id, name)
    )
;

    create table cy_connector_config (
        id number(19,0) not null,
        connectorClass varchar2(255 char),
        connectorName varchar2(255 char),
        globalPassword varchar2(255 char),
        globalUser varchar2(255 char),
        loginMode varchar2(255 char),
        name varchar2(255 char),
        primary key (id)
    )
;

    create table cy_connector_cred (
        id number(19,0) not null,
        password varchar2(255 char),
        username varchar2(255 char),
        connectorConfiguration_id number(19,0),
        user_id number(19,0),
        primary key (id)
    )
;

    create table cy_roundtrip (
        id number(19,0) not null,
        lastSync timestamp,
        lastSyncMode varchar2(255 char),
        name varchar2(255 char) unique,
        leftHandSide_id number(19,0),
        rightHandSide_id number(19,0),
        primary key (id)
    )
;

    create table cy_user (
        id number(19,0) not null,
        admin number(1,0) not null,
        email varchar2(255 char),
        name varchar2(255 char),
        password varchar2(255 char),
        primary key (id)
    )
;

    alter table cy_connector_attributes 
        add constraint FK1B26DCB29F2FC2CE 
        foreign key (configuration_id) 
        references cy_connector_config
;

    alter table cy_connector_cred 
        add constraint FKB57C4CE9607EEC3B 
        foreign key (user_id) 
        references cy_user
;

    alter table cy_connector_cred 
        add constraint FKB57C4CE995597D1B 
        foreign key (connectorConfiguration_id) 
        references cy_connector_config
;

    alter table cy_roundtrip 
        add constraint FKCE1A2ABB87F7FB 
        foreign key (rightHandSide_id) 
        references cy_bpmn_diagram
;

    alter table cy_roundtrip 
        add constraint FKCE1A2AB563C930 
        foreign key (leftHandSide_id) 
        references cy_bpmn_diagram
;

    create table cy_id_table (
         tablename varchar2(255 char),
         id number(10,0) 
    ) 
;
