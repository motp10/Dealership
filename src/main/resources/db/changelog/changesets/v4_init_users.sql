CREATE USER postgres_exporter WITH PASSWORD 'postgres_exporter';
GRANT pg_monitor TO postgres_exporter;