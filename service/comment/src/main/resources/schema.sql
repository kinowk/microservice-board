/* table */
create table comment (
    comment_id bigint not null primary key,
    content varchar(3000) not null,
    article_id bigint not null,
    parent_comment_id bigint not null,
    writer_id bigint not null,
    deleted bool not null,
    created_at datetime not null
);

/* index */
create index idx_article_id_parent_comment_id_comment_id on comment (
    article_id asc,
    parent_comment_id asc,
    comment_id asc
);

/* N번 페이지에서 M개의 댓글 조회 */
select *
from (
    select comment_id
    from comment
    where article_id = {article_id}
    order by parent_comment_id asc, comment_id asc
    limit {limit} offset {offset}
)t left join comment on t.comment_id = comment.comment_id;

/* 댓글 개수 조회 */
select
    count(*)
from (
    select comment_id
    from comment
    where article_id = {article_id}
    limit {limit}
);

/* 무한 스크롤 */
-- 1번 페이지
select *
from comment
where article_id = {article_id}
order by parent_comment_id asc, comment_id asc
limit {limit}

-- 2번 페이지 이상
select *
from comment
where article_id = {article_id}
and (
    parent_comment_id > {last_parent_comment_id} or (parent_comment_id = {last_parent_comment_id} and comment_id > {last_comment_id})
)
order by parent_comment_id asc, comment_id asc
limit {limit};

/* 댓글 무한 depth */
create table comment_v2 (
    comment_id bigint not null primary key,
    content varchar(3000) not null,
    article_id bigint not null,
    writer_id bigint not null,
    path varchar(25) character set utf8mb4 collate utf8mb4_bin not null,
    deleted bool not null,
    created_at datetime not null
);

create unique index idx_article_id_path on comment_v2 (article_id asc, path asc);

/* descendantsTopPath 조회 */
select path
from comment_v2
where article_id = {article_id}
and path > {parentPath} -- parent 본인은 미포함 검색 조건
and path like {parentPath}% -- parentPath를 prefix로 하는 모든 자손 검색 조건
order by patth desc limit 1; -- 조회 결과에서 가장 큰 path

/* 목록 조회 */
select *
from (
    select comment_id
    from comment_v2
    where article_id = {article_id}
    order by path asc
    limit {limit} offset {offset}
) t left join comment_v2 on t.comment_id = comment_v2.comment_id;

/* 카운트 조회 */
select count(*)
from (
    select comment_id
    from comment_v2
    where article_id = {article_id}
    limit {limit}
);

/* 무한 스크롤 */
-- 1번 페이지
select *
from comment_v2
where article_id = {article_id}
order by path asc
limit {limit};

-- 2번 페이지 이상
select *
from comment_v2
where article_id = {article_id}
and path > {last_path}
order by path asc
limit {limit};

/* 댓글 수 table */
create table article_comment_count (
    article_id bigint not null primary key,
    comment_count bigint not null
);