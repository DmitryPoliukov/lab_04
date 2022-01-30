insert into gift_certificate (id, name, description, price, duration, create_date, last_update_date)
values (1, 'certificate1', 'cool cerf', 1.33, 30, now(), now()),
       (2, 'certificate2', 'certific description', 2.33, 30, now(), now());

insert into tag (id, name)
values (1, 'tag1'),
       (2, 'tag2');


insert into gift_certificate_m2m_tag (gift_certificate_id, tag_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1);
