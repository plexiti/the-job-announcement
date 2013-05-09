
    create table cy_bpmn_diagram (
        id bigint not null,
        connectorId bigint,
        diagramPath varchar(255),
        label varchar(255),
        lastModified timestamp,
        lastSync timestamp,
        modeler varchar(255),
        status varchar(255),
        primary key (id)
    )
;

    create table cy_connector_attributes (
        configuration_id bigint not null,
        value varchar(255),
        name varchar(255) not null,
        primary key (configuration_id, name)
    )
;

    create table cy_connector_config (
        id bigint not null,
        connectorClass varchar(255),
        connectorName varchar(255),
        globalPassword varchar(255),
        globalUser varchar(255),
        loginMode varchar(255),
        name varchar(255),
        primary key (id)
    )
;

    create table cy_connector_cred (
        id bigint not null,
        password varchar(255),
        username varchar(255),
        connectorConfiguration_id bigint,
        user_id bigint,
        primary key (id)
    )
;

    create table cy_roundtrip (
        id bigint not null,
        lastSync timestamp,
        lastSyncMode varchar(255),
        name varchar(255) unique,
        leftHandSide_id bigint,
        rightHandSide_id bigint,
        primary key (id)
    )
;

    create table cy_user (
        id bigint not null,
        admin boolean not null,
        email varchar(255),
        name varchar(255),
        password varchar(255),
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
         tablename varchar(255),
         id integer 
    ) 
;
