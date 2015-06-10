CREATE OR REPLACE VIEW vistaxenviarcorreo AS 
 SELECT e.corremoemisor,
    e.hostcorreo,
    e.passowrdcorreoemisor,
    e.puertocorreo,
    e.starttls,
    e.transportecorreo,
    e.usaauthencorreo,
    c.claveacceso,
    c.autorizacion,
    c.beneficiario,
    c.fecha_a,
    c.id,
    e.ruc,
    d.correoelectronico
   FROM comprobanteelectronico c,
    entidad e,
    demografiacliente d
  WHERE e.habilitarnotificacioncorreo IS TRUE AND e.corremoemisor IS NOT NULL AND e.hostcorreo IS NOT NULL AND e.passowrdcorreoemisor IS NOT NULL AND e.puertocorreo IS NOT NULL AND c.entidademisora_id = e.id AND c.beneficiario::text = d.identificacion::text AND c.autorizado = true AND c.nbc = false
  ORDER BY e.corremoemisor;




create table contingencia_01(check (entidad_id=1)) inherits (contingencia);
create table contingencia_02(check (entidad_id=2)) inherits (contingencia);
create table contingencia_03(check (entidad_id=3)) inherits (contingencia);
create table contingencia_04(check (entidad_id=4)) inherits (contingencia);
create table contingencia_05(check (entidad_id=5)) inherits (contingencia);
create table contingencia_06(check (entidad_id=6)) inherits (contingencia);
create table contingencia_07(check (entidad_id=7)) inherits (contingencia);
create table contingencia_08(check (entidad_id=8)) inherits (contingencia);
create table contingencia_09(check (entidad_id=9)) inherits (contingencia);
create table contingencia_10(check (entidad_id=10)) inherits (contingencia);

create or replace function insertcontingencia() returns trigger as $$
begin
        if NEW.entidad_id=1 then insert into contingencia_01 values(NEW.*); end if;
        if NEW.entidad_id=2 then insert into contingencia_02 values(NEW.*); end if;
        if NEW.entidad_id=3 then insert into contingencia_03 values(NEW.*); end if;
        if NEW.entidad_id=4 then insert into contingencia_04 values(NEW.*); end if;
        if NEW.entidad_id=5 then insert into contingencia_05 values(NEW.*); end if;
        if NEW.entidad_id=6 then insert into contingencia_06 values(NEW.*); end if;
        if NEW.entidad_id=7 then insert into contingencia_07 values(NEW.*); end if;
        if NEW.entidad_id=8 then insert into contingencia_08 values(NEW.*); end if;
        if NEW.entidad_id=9 then insert into contingencia_09 values(NEW.*); end if;
        if NEW.entidad_id=10 then insert into contingencia_10 values(NEW.*); end if;
        return NULL;
end;
$$ language 'plpgsql';


create  trigger insert_contingencia_trigger before insert on contingencia for each row execute procedure insertcontingencia();
                                                                                                                                         
