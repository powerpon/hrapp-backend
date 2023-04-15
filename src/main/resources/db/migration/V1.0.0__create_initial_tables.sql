CREATE TABLE IF NOT EXISTS candidate(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    contact_number VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS skill(
    id SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS candidate_skill(
    candidate_id VARCHAR(255) NOT NULL,
    skill_id INT NOT NULL,
    PRIMARY KEY (candidate_id, skill_id),
    FOREIGN KEY (candidate_id)
    REFERENCES candidate(id),
    FOREIGN KEY (skill_id)
    REFERENCES skill(id)
);