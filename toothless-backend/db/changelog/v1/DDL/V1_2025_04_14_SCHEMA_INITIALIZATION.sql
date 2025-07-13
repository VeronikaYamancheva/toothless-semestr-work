CREATE TABLE user_data
(
    user_id        UUID,
    first_name     VARCHAR(50),
    last_name      VARCHAR(50),
    middle_name    VARCHAR(50),
    birth_date     DATE,
    priority_email VARCHAR(100),
    phone_number   VARCHAR(20),
    state          VARCHAR(20),
    admin_approval BOOLEAN,
    avatar_url     VARCHAR,
    ---------------------------------------------------------------------------
    CONSTRAINT user_data_user_id_pk PRIMARY KEY (user_id),
    CONSTRAINT user_data_email_nn CHECK ( priority_email IS NOT NULL ),
    CONSTRAINT user_data_email_un UNIQUE (priority_email),
    CONSTRAINT user_data_admin_approval_nn CHECK ( admin_approval IS NOT NULL ),
    CONSTRAINT user_data_state_nn CHECK ( state IS NOT NULL )
);

CREATE TABLE user_credentials
(
    credential_id UUID,
    user_id       UUID,
    provider_type VARCHAR(20),
    provider_key  VARCHAR(255) NOT NULL,
    hash_password VARCHAR,
    -------------------------------------------------------------------------------
    CONSTRAINT acc_cr_credentials_id_pk PRIMARY KEY (credential_id),
    CONSTRAINT acc_cr_user_id_nn CHECK ( user_id IS NOT NULL ),
    CONSTRAINT acc_cr_user_id_fk FOREIGN KEY (user_id) REFERENCES user_data (user_id),
    CONSTRAINT acc_cr_provider_type_nn CHECK ( provider_type IS NOT NULL ),
    CONSTRAINT acc_cr_provider_key_nn CHECK ( provider_key IS NOT NULL ),
    CONSTRAINT unique_provider_pair UNIQUE (provider_type, provider_key)
);

CREATE TABLE user_role
(
    user_id UUID,
    role    VARCHAR(20),
    -----------------------------------------------------------------------------
    CONSTRAINT us_pk PRIMARY KEY (user_id, role),
    CONSTRAINT us_r_user_id_nn CHECK ( user_id IS NOT NULL),
    CONSTRAINT us_r_user_id_fk FOREIGN KEY (user_id) REFERENCES user_data (user_id),
    CONSTRAINT us_r_role_nn CHECK ( role IS NOT NULL )
);

CREATE TABLE client
(
    client_id              UUID,
    user_id                UUID,
    passport_series_number VARCHAR,
    passport_issued_by     VARCHAR,
    passport_issue_date    VARCHAR,
    passport_division_code VARCHAR,
    policy_number          VARCHAR,
    snils                  VARCHAR,
    -------------------------------------------------------------------------------------
    CONSTRAINT cl_client_id_pk PRIMARY KEY (client_id),
    CONSTRAINT cl_user_id_fk FOREIGN KEY (user_id) REFERENCES user_data (user_id),
    CONSTRAINT cl_user_id_nn CHECK ( user_id IS NOT NULL),
    CONSTRAINT cl_passport_series_number_un UNIQUE (passport_series_number),
    CONSTRAINT cl_policy_number_un UNIQUE (policy_number),
    CONSTRAINT cl_snils_un UNIQUE (snils)
);

CREATE TABLE specialization
(
    specialization_id UUID,
    name              VARCHAR(100),
    -------------------------------------------------------------------
    CONSTRAINT sp_specialization_id_pk PRIMARY KEY (specialization_id),
    CONSTRAINT sp_name_nn CHECK ( name IS NOT NULL ),
    CONSTRAINT sp_name_un UNIQUE (name)
);

CREATE TABLE procedure
(
    procedure_id      UUID,
    name              VARCHAR(64),
    description       VARCHAR(255),
    cost              INTEGER,
    is_active         BOOLEAN DEFAULT true,
    specialization_id UUID,
    ---------------------------------------------------------
    CONSTRAINT pr_procedure_id_pk PRIMARY KEY (procedure_id),
    CONSTRAINT pr_name_nn CHECK ( name IS NOT NULL ),
    CONSTRAINT pr_description_nn CHECK ( description IS NOT NULL ),
    CONSTRAINT pr_cost_nn CHECK ( cost IS NOT NULL ),
    CONSTRAINT pr_specialization_id_nn CHECK ( specialization_id IS NOT NULL ),
    CONSTRAINT pr_specialization_id_fk FOREIGN KEY (specialization_id) REFERENCES specialization(specialization_id)
);

CREATE TABLE dentist
(
    dentist_id     UUID,
    user_id        UUID,
    specialization UUID,
    experience     INTEGER,
    education      VARCHAR(255),
    about          TEXT,
    --------------------------------------------------------------------------------------------------------------
    CONSTRAINT den_dentist_id_pk PRIMARY KEY (dentist_id),
    CONSTRAINT den_user_id_fk FOREIGN KEY (user_id) REFERENCES user_data (user_id),
    CONSTRAINT cl_user_id_nn CHECK ( user_id IS NOT NULL),
    CONSTRAINT den_specialization_id_fk FOREIGN KEY (specialization) REFERENCES specialization (specialization_id)
);

CREATE TABLE comment
(
    comment_id UUID,
    author_id  UUID,
    content    VARCHAR(255),
    dentist_id UUID,
    date_time  TIMESTAMP,
    parent_id  UUID,
    --------------------------------------------------------------------------------------
    CONSTRAINT com_comment_id_pk PRIMARY KEY (comment_id),
    CONSTRAINT com_author_id_nn CHECK ( author_id IS NOT NULL ),
    CONSTRAINT com_author_id_fk FOREIGN KEY (author_id) REFERENCES user_data (user_id),
    CONSTRAINT com_content_nn CHECK ( content IS NOT NULL),
    CONSTRAINT com_dentist_id_fk FOREIGN KEY (dentist_id) REFERENCES dentist (dentist_id),
    CONSTRAINT com_date_time_nn CHECK ( date_time IS NOT NULL )
);

CREATE TABLE appointment_form
(
    form_id               UUID,
    expected_procedure_id UUId,
    complaints            TEXT,
    symptoms              TEXT,
    diagnosis             TEXT,
    procedures_done       TEXT,
    recommendations       TEXT,
    treatment_plan        TEXT,
    -------------------------------------------------
    CONSTRAINT app_f_form_id_pk PRIMARY KEY (form_id),
    CONSTRAINT app_f_expected_procedure_id_fk FOREIGN KEY (expected_procedure_id) REFERENCES procedure (procedure_id)
);

CREATE TABLE appointment
(
    appointment_id      UUID,
    client_id           UUID,
    dentist_id          UUID,
    begin_time          TIME,
    end_time            TIME,
    date                DATE,
    appointment_form_id UUID,
    --------------------------------------------------------------------------------------------------
    CONSTRAINT app_appointment_id_pk PRIMARY KEY (appointment_id),
    CONSTRAINT app_client_id_nn CHECK ( client_id IS NOT NULL ),
    CONSTRAINT app_client_id_fk FOREIGN KEY (client_id) REFERENCES user_data (user_id),
    CONSTRAINT app_dentist_id_nn CHECK ( dentist_id IS NOT NULL ),
    CONSTRAINT app_dentist_id_fk FOREIGN KEY (dentist_id) REFERENCES dentist (dentist_id),
    CONSTRAINT app_form_id_fk FOREIGN KEY (appointment_form_id) REFERENCES appointment_form (form_id),
    CONSTRAINT app_begin_time_nn CHECK ( begin_time IS NOT NULL ),
    CONSTRAINT app_end_time_nn CHECK ( end_time IS NOT NULL ),
    CONSTRAINT app_date_nn CHECK ( date IS NOT NULL )
);

