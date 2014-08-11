
-----------------------------------------------------------------------------
-- vuser
-----------------------------------------------------------------------------





CREATE TABLE {schema}.vuser
(
    id INTEGER NOT NULL,
    login VARCHAR(30) NOT NULL,
    password VARCHAR(128) NOT NULL,
    salt VARCHAR(256),
    active INT2 default 0 NOT NULL,
    activation_code VARCHAR(64) NOT NULL,
    premium INT2 default 0 NOT NULL,
    email VARCHAR(100) NOT NULL,
    points DOUBLE PRECISION default 0.0 NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE {schema}.vuser OWNER TO {schema};

--

COMMENT ON TABLE {schema}.vuser IS 'User data';



-----------------------------------------------------------------------------
-- alert_types
-----------------------------------------------------------------------------





CREATE TABLE {schema}.alert_types
(
    id INTEGER NOT NULL,
    type_name VARCHAR(30) NOT NULL,
    type_description VARCHAR(255),
    picture BYTEA NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE {schema}.alert_types OWNER TO {schema};

--

COMMENT ON TABLE {schema}.alert_types IS 'Types of alert';



-----------------------------------------------------------------------------
-- alert
-----------------------------------------------------------------------------





CREATE TABLE {schema}.alert
(
    id INTEGER NOT NULL,
    vuser_id INTEGER NOT NULL,
    type INTEGER NOT NULL,
    lat DOUBLE PRECISION NOT NULL,
    lng DOUBLE PRECISION NOT NULL,
    line VARCHAR(10),
    description VARCHAR(255),
    PRIMARY KEY (id)
);
ALTER TABLE {schema}.alert OWNER TO {schema};

--

COMMENT ON TABLE {schema}.alert IS 'Types of alert';



-----------------------------------------------------------------------------
-- bug_tracer
-----------------------------------------------------------------------------





CREATE TABLE {schema}.bug_tracer
(
    id INTEGER NOT NULL,
    vuser_id INTEGER NOT NULL,
    title VARCHAR(30) NOT NULL,
    content VARCHAR(255) NOT NULL,
    report_time TIMESTAMP NOT NULL,
    status INTEGER NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE {schema}.bug_tracer OWNER TO {schema};

--

COMMENT ON TABLE {schema}.bug_tracer IS 'Bug report system';



-----------------------------------------------------------------------------
-- sessions
-----------------------------------------------------------------------------





CREATE TABLE {schema}.sessions
(
    id INTEGER NOT NULL,
    vuser_id INTEGER NOT NULL,
    expiry_time TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE {schema}.sessions OWNER TO {schema};

--

COMMENT ON TABLE {schema}.sessions IS 'User sessions';



----------------------------------------------------------------------
-- sessions
----------------------------------------------------------------------


    

----------------------------------------------------------------------
-- vuser
----------------------------------------------------------------------


    

----------------------------------------------------------------------
-- alert_types
----------------------------------------------------------------------


    
{start}
set role {schema};

ALTER TABLE alert
    ADD CONSTRAINT alert_FK_1 FOREIGN KEY (vuser_id)
    REFERENCES vuser (id)
;
{end}
{start}
set role {schema};

ALTER TABLE alert
    ADD CONSTRAINT alert_FK_2 FOREIGN KEY (type)
    REFERENCES alert_types (id)
;
{end}

----------------------------------------------------------------------
-- alert
----------------------------------------------------------------------


    
{start}
set role {schema};

ALTER TABLE bug_tracer
    ADD CONSTRAINT bug_tracer_FK_1 FOREIGN KEY (vuser_id)
    REFERENCES vuser (id)
;
{end}

----------------------------------------------------------------------
-- bug_tracer
----------------------------------------------------------------------


    
{start}
set role {schema};

ALTER TABLE sessions
    ADD CONSTRAINT sessions_FK_1 FOREIGN KEY (vuser_id)
    REFERENCES vuser (id)
;
{end}
