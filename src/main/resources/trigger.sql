CREATE OR REPLACE TRIGGER `add_like` AFTER INSERT ON `board_like` FOR EACH ROW BEGIN
UPDATE board SET board.likes = board.likes+1 WHERE board.bno = NEW.board_bno;
END

CREATE OR REPLACE TRIGGER `delete_like` AFTER DELETE ON `board_like` FOR EACH ROW BEGIN
UPDATE board SET board.likes = board.likes-1 WHERE board.bno = OLD.board_bno;
END

CREATE OR REPLACE TRIGGER `add_cart` AFTER INSERT ON `cart` FOR EACH ROW BEGIN
UPDATE place SET place.cart = place.cart+1 WHERE place.pno = NEW.place_pno;
END

CREATE OR REPLACE TRIGGER `delete_cart` AFTER DELETE ON `cart` FOR EACH ROW BEGIN
UPDATE place SET place.cart = place.cart-1 WHERE place.pno = OLD.place_pno;
END

CREATE OR REPLACE TRIGGER `add_disciplinary` AFTER INSERT ON `disciplinary` FOR EACH ROW
BEGIN
UPDATE member set member.disciplinary = member.disciplinary+1 WHERE member.mno=NEW.member_mno;
END

