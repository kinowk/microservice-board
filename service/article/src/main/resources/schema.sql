/* table */
create table article (
    article_id bigint not null primary key,
    title varchar(100) not null,
    content varchar(3000) not null,
    board_id bigint not null,
    writer_id bigint not null,
    created_at datetime not null,
    updated_at datetime not null
);

/* index */
create index idx_board_id_article_id on article(board_id asc, article_id desc);

/* Covering Index를 활용하여 offset 1499970에서부터 Clustered Index에 접근 */
select *
from (
    select article_id
    from article
    where board_id = 1
    order by article_id desc
    limit 30 offset 1499970에서부터
) sub
left outer join article on sub.article_id = article.article_id;

/* 게시글 개수 */
select count(*)
from (
    select article_id
    from article
    where board_id = 1
    limit 300301
) sub;

/* 무한 스크롤 설계 */
/* 1번 페이지 */
select *
from article
where board_id = {board_id}
order by article_id desc
limit {limit};

/* 2번 페이지 이상 */
select *
from article
where board_id = {board_Id}
and article_id < {last_article_id}
order by article_id desc
limit {limit};

/* 게시글 수 table */
create table board_article_count (
    board_id bigint not null primary key,
    article_count bigint not null
);

/* Transactional outbox 설계 */
create table outbox (
    outbox_id bigint not null primary key,
    shard_key bigint not null,
    event_type varchar(100) not null,
    payload varchar(5000) not null,
    created_at datetime not null
);
create index idx_shard_key_created_at on outbox(shard_key asc, created_at asc);