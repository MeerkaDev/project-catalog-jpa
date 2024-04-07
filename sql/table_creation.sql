create table public.categories
(
    id   bigserial
        primary key,
    name varchar not null
        unique
);

create table public.specs_categories
(
    id          bigserial
        primary key,
    category_id bigint  not null
        references public.categories,
    name        varchar not null
);

create table public.products
(
    id          bigserial
        primary key,
    category_id bigint  not null
        references public.categories,
    name        varchar not null,
    price       integer not null
);


create table public.values_specs
(
    id                  bigserial
        primary key,
    specs_categories_id bigint  not null
        references public.specs_categories,
    product_id          bigint  not null
        references public.products,
    value               varchar not null
);