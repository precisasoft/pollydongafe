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
create table contingencia_11(check (entidad_id=11)) inherits (contingencia);
create table contingencia_12(check (entidad_id=12)) inherits (contingencia);

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
	if NEW.entidad_id=11 then insert into contingencia_11 values(NEW.*); end if;
	if NEW.entidad_id=12 then insert into contingencia_12 values(NEW.*); end if;
        return NULL;
end;
$$ language 'plpgsql';


create  trigger insert_contingencia_trigger before insert on contingencia for each row execute procedure insertcontingencia();
 

create table documentofirmado_01(check (entidad_id=1)) inherits (documentofirmado);
create table documentofirmado_02(check (entidad_id=2)) inherits(documentofirmado);
create table documentofirmado_03(check (entidad_id=3)) inherits (documentofirmado);
create table documentofirmado_04(check (entidad_id=4)) inherits(documentofirmado);
create table documentofirmado_05(check (entidad_id=5)) inherits (documentofirmado);
create table documentofirmado_06(check (entidad_id=6)) inherits(documentofirmado);
create table documentofirmado_07(check (entidad_id=7)) inherits (documentofirmado);
create table documentofirmado_08(check (entidad_id=8)) inherits(documentofirmado);
create table documentofirmado_09(check (entidad_id=9)) inherits (documentofirmado);
create table documentofirmado_10(check (entidad_id=10)) inherits(documentofirmado);
create table documentofirmado_11(check (entidad_id=11)) inherits(documentofirmado);
create table documentofirmado_12(check (entidad_id=12)) inherits(documentofirmado);

create or replace function insert_documentofirmado() returns trigger as $$
begin
	if NEW.entidad_id=1 then insert into documentofirmado_01 values(NEW.*); end if;
	if NEW.entidad_id=2 then insert into documentofirmado_02 values(NEW.*); end if;
	if NEW.entidad_id=3 then insert into documentofirmado_03 values(NEW.*); end if;
	if NEW.entidad_id=4 then insert into documentofirmado_04 values(NEW.*); end if;
	if NEW.entidad_id=5 then insert into documentofirmado_05 values(NEW.*); end if;
	if NEW.entidad_id=6 then insert into documentofirmado_06 values(NEW.*); end if;
	if NEW.entidad_id=7 then insert into documentofirmado_07 values(NEW.*); end if;
	if NEW.entidad_id=8 then insert into documentofirmado_08 values(NEW.*); end if;
	if NEW.entidad_id=9 then insert into documentofirmado_09 values(NEW.*); end if;
	if NEW.entidad_id=10 then insert into documentofirmado_10 values(NEW.*); end if;
	if NEW.entidad_id=11 then insert into documentofirmado_11 values(NEW.*); end if;
	if NEW.entidad_id=12 then insert into documentofirmado_12 values(NEW.*); end if;

     -- if NEW.entidad_id=3 then insert into documentofirmado_03 values(NEW.*); end if;
	return null;
end;
$$ language 'plpgsql';
create trigger insert_documentofimrado before insert on documentofirmado for each row execute procedure insert_documentofirmado();
                                                                                                                                        
--- 
create table comprobanteelectronico_00(check (entidademisora_id=null)) inherits(comprobanteelectronico);
create table comprobanteelectronico_01(check (entidademisora_id=1)) inherits (comprobanteelectronico);
create table comprobanteelectronico_02(check(entidademisora_id=2)) inherits(comprobanteelectronico);
create table comprobanteelectronico_03(check (entidademisora_id=3)) inherits (comprobanteelectronico);
create table comprobanteelectronico_04(check(entidademisora_id=4)) inherits(comprobanteelectronico);
create table comprobanteelectronico_05(check (entidademisora_id=5)) inherits (comprobanteelectronico);
create table comprobanteelectronico_06(check(entidademisora_id=6)) inherits(comprobanteelectronico);
create table comprobanteelectronico_07(check (entidademisora_id=7)) inherits (comprobanteelectronico);
create table comprobanteelectronico_08(check(entidademisora_id=8)) inherits(comprobanteelectronico);
create table comprobanteelectronico_09(check (entidademisora_id=9)) inherits (comprobanteelectronico);
create table comprobanteelectronico_10(check(entidademisora_id=10)) inherits(comprobanteelectronico);
create table comprobanteelectronico_11(check (entidademisora_id=11)) inherits (comprobanteelectronico);
create table comprobanteelectronico_12(check (entidademisora_id=12)) inherits(comprobanteelectronico);
create or replace function insert_comprobanteelectronico() returns trigger as $$
begin

	if NEW.fecha_e is null then NEW.fecha_e=NEW.fecha_a; end if;
	if NEW.entidademisora_id=null then insert into comprobanteelectronico_00 values(NEW.*); end if;
	if NEW.entidademisora_id=1 then insert into comprobanteelectronico_01 values(NEW.*); end if;
	if NEW.entidademisora_id=2 then insert into comprobanteelectronico_02 values(NEW.*); end if; 	
	if NEW.entidademisora_id=3 then insert into comprobanteelectronico_03 values(NEW.*); end if;
	if NEW.entidademisora_id=4 then insert into comprobanteelectronico_04 values(NEW.*); end if; 
	if NEW.entidademisora_id=5 then insert into comprobanteelectronico_05 values(NEW.*); end if;
	if NEW.entidademisora_id=6 then insert into comprobanteelectronico_06 values(NEW.*); end if; 
	if NEW.entidademisora_id=7 then insert into comprobanteelectronico_71 values(NEW.*); end if;
	if NEW.entidademisora_id=8 then insert into comprobanteelectronico_08 values(NEW.*); end if; 
	if NEW.entidademisora_id=9 then insert into comprobanteelectronico_09 values(NEW.*); end if;
	if NEW.entidademisora_id=10 then insert into comprobanteelectronico_10 values(NEW.*); end if; 
	if NEW.entidademisora_id=11 then insert into comprobanteelectronico_11 values(NEW.*); end if;
	if NEW.entidademisora_id=12 then insert into comprobanteelectronico_12 values(NEW.*); end if;
	return null;

end;
$$ language 'plpgsql';
create trigger insert_comprobanteleectronico before insert on comprobanteelectronico for each row execute procedure insert_comprobanteelectronico();
