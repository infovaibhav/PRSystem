
 
/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(type) VALUES ('ADMIN');
INSERT INTO USER_PROFILE(type) VALUES ('USER');
INSERT INTO USER_PROFILE(type) VALUES ('SUPERVISOR');
INSERT INTO USER_PROFILE(type) VALUES ('MANAGER');
INSERT INTO USER_PROFILE(type) VALUES ('PURCHASE_USER');
INSERT INTO USER_PROFILE(type) VALUES ('PURCHASE_SUPERVISOR');
INSERT INTO USER_PROFILE(type) VALUES ('PURCHASE_MANAGER');
INSERT INTO USER_PROFILE(type) VALUES ('STORE_USER');
 
 
/* Populate one Admin User which will further create other users for the application using GUI */
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email, authorized_transaction_limit, reporting_to, is_root, is_active)
VALUES ('root','$2a$10$SILNAmruyg2MVCIkXAKC4uJqwir76dNllnaM0bll3G5Ido1KIv9v2', 'Root','Root','abc@gmail.com', 0, null, true, true);

INSERT INTO APP_USER(sso_id, password, first_name, last_name, email, authorized_transaction_limit, reporting_to, is_root, is_active)
VALUES ('manager','$2a$10$SILNAmruyg2MVCIkXAKC4uJqwir76dNllnaM0bll3G5Ido1KIv9v2', 'Manager','Manager','abc@gmail.com', null, null, false, true);

INSERT INTO APP_USER(sso_id, password, first_name, last_name, email, authorized_transaction_limit, reporting_to, is_root, is_active)
VALUES ('admin','$2a$10$SILNAmruyg2MVCIkXAKC4uJqwir76dNllnaM0bll3G5Ido1KIv9v2', 'Admin','Admin','abc@gmail.com', 0, 2, false, true);

INSERT INTO APP_USER(sso_id, password, first_name, last_name, email, authorized_transaction_limit, reporting_to, is_root, is_active)
VALUES ('supervisor','$2a$10$SILNAmruyg2MVCIkXAKC4uJqwir76dNllnaM0bll3G5Ido1KIv9v2', 'Manager','Manager','abc@gmail.com', 0, 2, false, true);

INSERT INTO APP_USER(sso_id, password, first_name, last_name, email, authorized_transaction_limit, reporting_to, is_root, is_active)
VALUES ('user','$2a$10$SILNAmruyg2MVCIkXAKC4uJqwir76dNllnaM0bll3G5Ido1KIv9v2', 'Manager','Manager','abc@gmail.com', 0, 4, false, true);

INSERT INTO APP_USER(sso_id, password, first_name, last_name, email, authorized_transaction_limit, reporting_to, is_root, is_active)
VALUES ('purchase_supervisor','$2a$10$SILNAmruyg2MVCIkXAKC4uJqwir76dNllnaM0bll3G5Ido1KIv9v2', 'Purchase','Supervisor','abc@gmail.com', 5000, 2, false, true);

INSERT INTO APP_USER(sso_id, password, first_name, last_name, email, authorized_transaction_limit, reporting_to, is_root, is_active)
VALUES ('purchase_user','$2a$10$SILNAmruyg2MVCIkXAKC4uJqwir76dNllnaM0bll3G5Ido1KIv9v2', 'Purchase','User','abc@gmail.com', 0, 6, false, true);

INSERT INTO APP_USER(sso_id, password, first_name, last_name, email, authorized_transaction_limit, reporting_to, is_root, is_active)
VALUES ('store_user','$2a$10$SILNAmruyg2MVCIkXAKC4uJqwir76dNllnaM0bll3G5Ido1KIv9v2', 'Purchase','Supervisor','abc@gmail.com', 0, 2, false, true);

/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT app_user.id, profile.id FROM app_user app_user, user_profile profile
  where app_user.sso_id='root' and profile.type in ('ADMIN');

INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT app_user.id, profile.id FROM app_user app_user, user_profile profile
  where app_user.sso_id='manager' and profile.type in ('ADMIN', 'MANAGER', 'PURCHASE_MANAGER');
  
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT app_user.id, profile.id FROM app_user app_user, user_profile profile
  where app_user.sso_id='admin' and profile.type in ('ADMIN');

INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT app_user.id, profile.id FROM app_user app_user, user_profile profile
  where app_user.sso_id='supervisor' and profile.type in ('SUPERVISOR');

INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT app_user.id, profile.id FROM app_user app_user, user_profile profile
  where app_user.sso_id='user' and profile.type in ('USER');

INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT app_user.id, profile.id FROM app_user app_user, user_profile profile
  where app_user.sso_id='purchase_supervisor' and profile.type in ('PURCHASE_SUPERVISOR');

INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT app_user.id, profile.id FROM app_user app_user, user_profile profile
  where app_user.sso_id='purchase_user' and profile.type in ('PURCHASE_USER');

INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT app_user.id, profile.id FROM app_user app_user, user_profile profile
  where app_user.sso_id='store_user' and profile.type in ('STORE_USER');



SELECT * FROM user_profile;
SELECT * FROM app_user;
SELECT * FROM APP_USER_USER_PROFILE;
SELECT * FROM PURCHASE_REQUISITION;
SELECT * FROM PURCHASE_REQUISITION_ITEMS;
--delete from APP_USER_USER_PROFILE where user_id in (SELECT id FROM app_user where sso_id='test');delete FROM app_user where sso_id='test';

update purchase_requisition_items set item_code='000' where item_code is null;
update purchase_requisition_items set required_by_date=current_date where required_by_date is null;