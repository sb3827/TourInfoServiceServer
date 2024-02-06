CREATE or replace PROCEDURE Count_pno(in MNO bigint )
BEGIN
SELECT
    m.mno,
    SUM(CASE WHEN p.local_address LIKE '부산광역시%' THEN 1 ELSE 0 END) AS '부산',
        SUM(CASE WHEN p.local_address LIKE '서울특별시%' THEN 1 ELSE 0 END) AS '서울',
        SUM(CASE WHEN p.local_address LIKE '대구광역시%' THEN 1 ELSE 0 END) AS '대구',
        SUM(CASE WHEN p.local_address LIKE '대전광역시%' THEN 1 ELSE 0 END) AS '대전',
        SUM(CASE WHEN p.local_address LIKE '강원%' THEN 1 ELSE 0 END) AS '강원도',
        SUM(CASE WHEN p.local_address LIKE '광주광역시%' THEN 1 ELSE 0 END) AS '광주',
        SUM(CASE WHEN p.local_address LIKE '경기도%' THEN 1 ELSE 0 END) AS '경기도',
        SUM(CASE WHEN p.local_address LIKE '인천광역시%' THEN 1 ELSE 0 END) AS '인천',
        SUM(CASE WHEN p.local_address LIKE '제주특별자치도%' THEN 1 ELSE 0 END) AS '제주',
        SUM(CASE WHEN p.local_address LIKE '충청북도%' THEN 1 ELSE 0 END) AS '충청북도',
        SUM(CASE WHEN p.local_address LIKE '경상북도%' THEN 1 ELSE 0 END) AS '경상북도',
        SUM(CASE WHEN p.local_address LIKE '전라북도%' THEN 1 ELSE 0 END) AS '전라북도',
        SUM(CASE WHEN p.local_address LIKE '세종특별자치시%' THEN 1 ELSE 0 END) AS '세종',
        SUM(CASE WHEN p.local_address LIKE '충청남도%' THEN 1 ELSE 0 END) AS '충청남도',
        SUM(CASE WHEN p.local_address LIKE '경상남도%' THEN 1 ELSE 0 END) AS '경상남도',
        SUM(CASE WHEN p.local_address LIKE '전라남도%' THEN 1 ELSE 0 END) AS '전라남도',
        SUM(CASE WHEN p.local_address LIKE '울산광역시%' THEN 1 ELSE 0 END) AS '울산'
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
  AND (
            p.local_address LIKE '부산광역시%'
        OR p.local_address LIKE '서울특별시%'
        OR p.local_address LIKE '대구광역시%'
        OR p.local_address LIKE '대전광역시%'
        OR p.local_address LIKE '강원%'
        OR p.local_address LIKE '광주광역시%'
        OR p.local_address LIKE '경기도%'
        OR p.local_address LIKE '인천광역시%'
        OR p.local_address LIKE '제주특별자치도%'
        OR p.local_address LIKE '충청북도%'
        OR p.local_address LIKE '경상북도%'
        OR p.local_address LIKE '전라북도%'
        OR p.local_address LIKE '세종특별자치시%'
        OR p.local_address LIKE '충청남도%'
        OR p.local_address LIKE '경상남도%'
        OR p.local_address LIKE '전라남도%'
        OR p.local_address LIKE '울산광역시%'
    );
END $$
DELIMITER ;


BEGIN
SELECT
    'busan' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '부산광역시%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'seoul' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '서울특별시%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'daegu' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '대구광역시%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'daejeon' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '대전광역시%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'kangwon' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '강원도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'gwangju' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '광주광역시%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'kyungki' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '경기도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'incheon' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '인천광역시%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'jeju' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '제주특별자치도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'northCC' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '충청북도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'northKS' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '경상북도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'northJL' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '전라북도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'sejong' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '세종특별자치시%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'southCC' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '충청남도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'southKS' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '경상남도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'soutnJL' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '전라남도%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno
UNION ALL
SELECT
    'ulsan' AS 지역,
    COALESCE(SUM(CASE WHEN p.local_address LIKE '울산광역시%' THEN 1 ELSE 0 END), 0) AS 횟수
FROM
    member m
        LEFT OUTER JOIN
    board b ON b.writer_mno = m.mno
        LEFT OUTER JOIN
    board_place bp ON bp.board_bno = b.bno
        LEFT OUTER JOIN
    place p ON p.pno = bp.place_pno
WHERE
        m.mno = mno;
END