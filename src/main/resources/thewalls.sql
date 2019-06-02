-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas generowania: 02 Cze 2019, 08:32
-- Wersja serwera: 5.7.26-0ubuntu0.16.04.1
-- Wersja PHP: 7.0.33-0ubuntu0.16.04.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `thewalls`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Messages`
--

CREATE TABLE `Messages` (
  `msgid` bigint(20) NOT NULL,
  `minigame` varchar(16) NOT NULL,
  `language` varchar(5) NOT NULL,
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `Messages`
--

INSERT INTO `Messages` (`msgid`, `minigame`, `language`, `path`, `message`) VALUES
(1, 'TheWalls', 'PL', 'scoreboard.website.address', '§bfajnyserw.pl'),
(2, 'TheWalls', 'EN', 'scoreboard.website.address', '§bfajnyserw.pl'),
(3, 'TheWalls', 'EN', 'thewalls.login.notspectator', '§cYour rank doesn\'t allow you to spectate! Buy VIP rank to be able to spectate!'),
(4, 'TheWalls', 'PL', 'thewalls.login.notspectator', '§cTwoja ranga nie pozwala na obserwowanie areny! Kup range VIP aby moc obserwowac!'),
(5, 'TheWalls', 'PL', 'thewalls.joininfo', '§7Witaj na Trybie §bTheWalls.'),
(6, 'TheWalls', 'EN', 'thewalls.joininfo', '§7Welcome to §bTheWalls §7minigame.'),
(7, 'TheWalls', 'EN', 'thewalls.countingcancelled', '§cNot enough players to start the game!'),
(8, 'TheWalls', 'PL', 'thewalls.countingcancelled', '§cZa malo graczy aby gra wystartowala!'),
(9, 'TheWalls', 'PL', 'thewalls.command.team.noargs', '§7Wpisz /team [1-4] aby wybrac druzyne.'),
(10, 'TheWalls', 'EN', 'thewalls.command.team.noargs', '§7Type /team [1-4] to chose the team.'),
(11, 'TheWalls', 'EN', 'thewalls.command.team.error', '§cYou must chose number between 1 and 4!'),
(12, 'TheWalls', 'PL', 'thewalls.command.team.error', '§cMusisz wybrac liczbe od 1 do 4!'),
(13, 'TheWalls', 'EN', 'thewalls.command.team.ingameerror', '§cYou can\'t change the team during the game!'),
(14, 'TheWalls', 'PL', 'thewalls.command.team.ingameerror', '§cNie mozesz zmienic druzyny podczas gry!'),
(15, 'TheWalls', 'PL', 'thewalls.command.team.fullteam', '§cPodana druzyna jest pelna!'),
(16, 'TheWalls', 'EN', 'thewalls.command.team.fullteam', '§cThis team is full!'),
(17, 'TheWalls', 'EN', 'thewalls.command.team.jointeamsuccess', '§7You successfully joined the §b{TEAM}§7!'),
(18, 'TheWalls', 'PL', 'thewalls.command.team.jointeamsuccess', '§7Pomyslnie dolaczyles do §b{TEAM}§7!'),
(19, 'TheWalls', 'PL', 'thewalls.command.team.alreadyinteam', '§7Jestes juz w tej druzynie!'),
(20, 'TheWalls', 'EN', 'thewalls.command.team.alreadyinteam', '§cYou are in this team already!'),
(21, 'TheWalls', 'PL', 'thewalls.exp.giveinfo', '§6Otrzymales 5 poziom doswiadczenia jako VIP!'),
(22, 'TheWalls', 'EN', 'thewalls.exp.giveinfo', '§6You received 5 levels of experience as VIP!'),
(23, 'TheWalls', 'PL', 'thewalls.msg.cantplacelava', '§cNie mozesz uzywac tego przedmiotu przed opadnieciem scian!'),
(24, 'TheWalls', 'EN', 'thewalls.msg.cantplacelava', '§cYou can\'t use that before walls fall.'),
(25, 'TheWalls', 'EN', 'thewalls.msg.cantuseitnow', '§cYou can\'t use that right now!'),
(26, 'TheWalls', 'PL', 'thewalls.msg.cantuseitnow', '§cNie mozesz tego uzyc w tym momencie!'),
(27, 'TheWalls', 'PL', 'thewalls.msg.surfacenotavailableyet', '§cNie mozesz tego uzyc w tym momencie!'),
(28, 'TheWalls', 'EN', 'thewalls.msg.surfacenotavailableyet', '§cYou can\'t use this command right now!'),
(29, 'TheWalls', 'EN', 'thewalls.notavailableforplayers', '§cThis is only VIP command!'),
(30, 'TheWalls', 'PL', 'thewalls.notavailableforplayers', '§cKomenda tylko dla VIPow!'),
(31, 'TheWalls', 'PL', 'thewalls.alreadyusedsurface', '§cKomenda §b/wyjdz §cjest dostepna tylko raz podczas gry!'),
(32, 'TheWalls', 'EN', 'thewalls.alreadyusedsurface', '§cCommand §b/surface §cis avaliable only once per game!'),
(33, 'TheWalls', 'PL', 'thewalls.scoreboard.kills', '§7Zabojstwa:§6 '),
(34, 'TheWalls', 'EN', 'thewalls.scoreboard.kills', '§7Kills:§6 '),
(35, 'TheWalls', 'PL', 'thewalls.scoreboard.deaths', '§7Smierci:§6 '),
(36, 'TheWalls', 'EN', 'thewalls.scoreboard.deaths', '§7Deaths:§6 '),
(37, 'TheWalls', 'PL', 'thewalls.scoreboard.wins', '§7Wygrane:§6 '),
(38, 'TheWalls', 'EN', 'thewalls.scoreboard.wins', '§7Wins:§6 '),
(39, 'TheWalls', 'PL', 'thewalls.scoreboard.lose', '§7Przegrane:§6 '),
(40, 'TheWalls', 'EN', 'thewalls.scoreboard.lose', '§7Loses:§6 '),
(41, 'TheWalls', 'EN', 'thewalls.scoreboard.money', '§7Coins:§b '),
(42, 'TheWalls', 'PL', 'thewalls.scoreboard.money', '§7Monety:§b '),
(43, 'TheWalls', 'PL', 'thewalls.scoreboard.ingame.TEAM1', '§aTeam1§7:§6 '),
(44, 'TheWalls', 'EN', 'thewalls.scoreboard.ingame.TEAM1', '§aTeam1§7:§6 '),
(45, 'TheWalls', 'EN', 'thewalls.scoreboard.ingame.TEAM2', '§bTeam2§7:§6 '),
(46, 'TheWalls', 'PL', 'thewalls.scoreboard.ingame.TEAM2', '§bTeam2§7:§6 '),
(47, 'TheWalls', 'PL', 'thewalls.scoreboard.ingame.TEAM3', '§cTeam3§7:§6 '),
(48, 'TheWalls', 'EN', 'thewalls.scoreboard.ingame.TEAM3', '§cTeam3§7:§6 '),
(49, 'TheWalls', 'EN', 'thewalls.scoreboard.ingame.TEAM4', '§eTeam4§7:§6 '),
(50, 'TheWalls', 'PL', 'thewalls.scoreboard.ingame.TEAM4', '§eTeam4§7:§6 '),
(51, 'TheWalls', 'PL', 'shop.item.price', '§bKoszt: §6{MONEY} §7monet'),
(52, 'TheWalls', 'EN', 'shop.item.price', '§bPrice: §6{MONEY} §7coins'),
(53, 'TheWalls', 'PL', 'shop.item.amount', '§bIlosc: §6{AMOUNT}'),
(54, 'TheWalls', 'EN', 'shop.item.amount', '§bAmount: §6{AMOUNT} '),
(55, 'TheWalls', 'EN', 'thewalls.shoponlyingame', '§cShop is only available during the game'),
(56, 'TheWalls', 'PL', 'thewalls.shoponlyingame', '§cSklep jest dostepny tylko podczas rozgrywki'),
(57, 'TheWalls', 'PL', 'shop.givenpermitems', '§6§l+ §bOtrzymales wszystkie zakupione itemy stale'),
(58, 'TheWalls', 'EN', 'shop.givenpermitems', '§6§l+ §bYou have received all purchased permanent items'),
(59, 'TheWalls', 'EN', 'thewalls.msg.furnacenowprotected', '§6§l+ §bYour furnace is now being protected'),
(60, 'TheWalls', 'PL', 'thewalls.msg.furnacenowprotected', '§6§l+ §bTwoj piecyk jest od teraz chroniony'),
(61, 'TheWalls', 'PL', 'thewalls.msg.furnacenolongerprotected', '§6§l+ §bTwoj piecyk nie jest juz chroniony'),
(62, 'TheWalls', 'EN', 'thewalls.msg.furnacenolongerprotected', '§6§l+ §bYour furnace is no longer being protected'),
(63, 'TheWalls', 'EN', 'thewalls.teleportsuccess', '§6§l+ §bYou have been teleported to §e{NICK}'),
(64, 'TheWalls', 'PL', 'thewalls.teleportsuccess', '§6§l+ §bZostales przeteleportowany do §e{NICK}'),
(65, 'TheWalls', 'PL', 'thewalls.playerteleported', '§6§l+ §6VIP §e{NICK} §bprzeteleportowal sie do Ciebie'),
(66, 'TheWalls', 'EN', 'thewalls.playerteleported', '§6§l+ §6VIP §e{NICK} §bhas teleported to you'),
(67, 'TheWalls', 'EN', 'thewalls.msg.givediamondforkill', '§6§l+ §6You have been rewarded with a §bdiamond §6for killing an enemy'),
(68, 'TheWalls', 'PL', 'thewalls.msg.givediamondforkill', '§6§l+ §6Otrzymales §bdiament §6za zabojstwo przeciwnika'),
(69, 'TheWalls', 'PL', 'thewalls.msg.moneyforkill', '§6§l+ §bDostales §e{MONEY} monet §bza zabojstwo przeciwnika'),
(70, 'TheWalls', 'EN', 'thewalls.msg.moneyforkill', '§6§l+ §bYou have been rewarded with §e{MONEY} coins §bfor killing an enemy'),
(71, 'TheWalls', 'PL', 'thewalls.game.win.team1', '§6§l+ §a§lWYGRAL TEAM ZIELONY [1]!'),
(72, 'TheWalls', 'EN', 'thewalls.game.win.team1', '§6§l+ §a§lTEAM GREEN [1] WON!'),
(73, 'TheWalls', 'PL', 'thewalls.game.win.team2', '§6§l+ §b§lWYGRAL TEAM NIEBIESKI [2]!'),
(74, 'TheWalls', 'EN', 'thewalls.game.win.team2', '§6§l+ §b§lTEAM BLUE [2] WON!'),
(75, 'TheWalls', 'EN', 'thewalls.game.win.team3', '§6§l+ §c§lTEAM RED [3] WON!'),
(76, 'TheWalls', 'PL', 'thewalls.game.win.team3', '§6§l+ §c§lWYGRAL TEAM CZERWONY [3]!'),
(77, 'TheWalls', 'PL', 'thewalls.game.win.team4', '§6§l+ §e§lWYGRAL TEAM ZOLTY [4]!'),
(78, 'TheWalls', 'EN', 'thewalls.game.win.team4', '§6§l+ §e§lTEAM YELLOW [4] WON!'),
(79, 'TheWalls', 'PL', 'shop.item.tempitems', '§6§l+ §dPrzedmioty jednorazowe §6§l+'),
(80, 'TheWalls', 'EN', 'shop.item.tempitems', '§6§l+ §dDisposable items §6§l+'),
(81, 'TheWalls', 'EN', 'shop.item.permitems', '§6§l+ §bPermanent items §6§l+'),
(82, 'TheWalls', 'PL', 'shop.item.permitems', '§6§l+ §bPrzedmioty stale §6§l+'),
(83, 'TheWalls', 'PL', 'thewalls.command.team.only2teams', '§cJest za malo graczy by wybrac wiecej niz 2 dryzyny'),
(84, 'TheWalls', 'EN', 'thewalls.command.team.only2teams', '§cNot enough players to chose more than first two teams'),
(85, 'TheWalls', 'PL', 'thewalls.countingstarted', '§6§l+++ §b§lODLICZANIE SIE ROZPOCZELO! §6§l+++'),
(86, 'TheWalls', 'EN', 'thewalls.countingstarted', '§6§l+++ §b§lTHE COUNTDOWN HAS BEGUN! §6§l+++'),
(87, 'TheWalls', 'EN', 'thewalls.msg.kitgiven', '§7You choosed §c{KIT} §7kit'),
(88, 'TheWalls', 'PL', 'thewalls.msg.kitgiven', '§7Wybrales kit §c{KIT}'),
(89, 'TheWalls', 'PL', 'thewalls.login.vipslots', '§6§l+ §bSloty zarezerwowane dla rangi VIP.\nMozesz zakupic range na -'),
(90, 'TheWalls', 'EN', 'thewalls.login.vipslots', '§6§l+ §bSlots only for VIP. To buy rank check: -'),
(91, 'TheWalls', 'PL', 'thewalls.msg.classgiven', '§bOtrzymales klase {CLASS}!'),
(92, 'TheWalls', 'EN', 'thewalls.msg.classgiven', '§bYou received {CLASS} class!'),
(93, 'TheWalls', 'EN', 'thewalls.login.restarting', '§cArena is restarting'),
(94, 'TheWalls', 'PL', 'thewalls.login.restarting', '§cArena sie restartuje!'),
(95, 'TheWalls', 'PL', 'shop.success', '§7[§6Sklep§7] §aPomyslnie zakupiono przedmiot.'),
(96, 'TheWalls', 'EN', 'shop.success', '§7[§6Shop§7] §aSuccessfully purchased an item.'),
(97, 'TheWalls', 'PL', 'thewalls.nowinners', '§7Nie ma zadnej wygranej druzyny!'),
(98, 'TheWalls', 'EN', 'thewalls.nowinners', '§7There are no winners!');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Players`
--

CREATE TABLE `Players` (
  `userid` bigint(20) NOT NULL,
  `language` varchar(4) NOT NULL,
  `username` varchar(16) NOT NULL,
  `lastip` varchar(16) NOT NULL,
  `experience` bigint(20) NOT NULL,
  `pets` varchar(5) NOT NULL,
  `effects` varchar(5) NOT NULL,
  `disguise` varchar(5) NOT NULL,
  `rank` varchar(18) NOT NULL,
  `rankto` text NOT NULL,
  `onlineTime` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Settings`
--

CREATE TABLE `Settings` (
  `settingsid` int(11) NOT NULL,
  `path` varchar(32) NOT NULL,
  `value` varchar(130) NOT NULL,
  `type` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `Settings`
--

INSERT INTO `Settings` (`settingsid`, `path`, `value`, `type`) VALUES
(1, 'thewalls.map.path', '/home/grzegorz/TheWalls/Mapy', 'TheWalls'),
(2, 'thewalls.minplayers', '20', 'TheWalls'),
(3, 'chat.admin', '§7[§c{LANG}§7][§cAdmin§7] §7{DISPLAYNAME} §7§l> §e{MESSAGE}', 'General'),
(4, 'chat.globalmod', '§7[§c{LANG}§7][§2GlobalMod§7] §7{DISPLAYNAME} §7§l> §e{MESSAGE}', 'General'),
(5, 'chat.mod', '§7[§c{LANG}§7][§aMod§7] §7{DISPLAYNAME} §7§l> §e{MESSAGE}', 'General'),
(6, 'chat.kidmod', '§7[§c{LANG}§7][§3KidMod§7] §7{DISPLAYNAME} §7§l> §e{MESSAGE}', 'General'),
(7, 'chat.helper', '§7[§c{LANG}§7][§bHelper§7] §7{DISPLAYNAME} §7§l> §e{MESSAGE}', 'General'),
(8, 'chat.youtube', '§7[§c{LANG}§7][§dYouTube§7] §7{DISPLAYNAME} §7§l> §f{MESSAGE}', 'General'),
(9, 'chat.vip', '§7[§c{LANG}§7][§6VIP§7] §7{DISPLAYNAME} §7§l> §f{MESSAGE}', 'General'),
(10, 'chat.gracz', '§7[§c{LANG}§7][§8Player§7] §7{DISPLAYNAME} §7§l> §f{MESSAGE}', 'General'),
(11, 'chat.headadmin', '§7[§c{LANG}§7][§4HeadAdmin§7] §7{DISPLAYNAME} §7§l> §6{MESSAGE}', 'General'),
(12, 'thewalls.countingtostarttime', '60', 'TheWalls'),
(13, 'thewalls.countingtodropwalls', '600', 'TheWalls'),
(14, 'thewalls.countingtodm', '420', 'TheWalls'),
(15, 'thewalls.countingtoend', '300', 'TheWalls'),
(16, 'thewalls.maxteamsize', '10', 'TheWalls'),
(17, 'thewalls.spawns.team.1', '-76.77:66:77.87:-138.60:4.35', 'TheWalls'),
(18, 'thewalls.spawns.team.2', '-76.64:66:-76.74:-42:3.74', 'TheWalls'),
(19, 'thewalls.spawns.team.3', '77.60:66:-76.62:-315.75:0.14', 'TheWalls'),
(20, 'thewalls.spawns.team.4', '77.69:66:77.74:-223.20:-1.34', 'TheWalls'),
(21, 'thewalls.spawns.dm.team.1', '180.67:95:0.68:1.19:115.5', 'TheWalls'),
(22, 'thewalls.spawns.dm.team.2', '140.90:95:-37.27:-6.75:1.80', 'TheWalls'),
(23, 'thewalls.spawns.dm.team.3', '103.02:95:0.24:-2.40:-88.49', 'TheWalls'),
(24, 'thewalls.spawns.dm.team.4', '141.83:95:38:-5.85:-180', 'TheWalls'),
(26, 'thewalls.multiplier', '1', 'TheWalls'),
(27, 'thewalls.moneyforwin', '15', 'TheWalls'),
(28, 'thewalls.expforkill', '10', 'TheWalls'),
(29, 'thewalls.expforwin', '30', 'TheWalls'),
(30, 'thewalls.moneyforkill', '5', 'TheWalls'),
(31, 'chat.miniyt', '§7[§c{LANG}§7][§eminiYT§7] §7{DISPLAYNAME} §7§l> §f{MESSAGE}', 'General'),
(32, 'thewalls.minplayersearly', '18', 'TheWalls'),
(33, 'chat.programista', '§7[§c{LANG}§7][§bProgramista§7]§7 {DISPLAYNAME} §7§l> §6{MESSAGE}', 'General'),
(34, 'thewalls.numberofmaps', '5', 'TheWalls');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `TheWallsMoney`
--

CREATE TABLE `TheWallsMoney` (
  `id` bigint(20) NOT NULL,
  `money` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `TheWallsShopItems`
--

CREATE TABLE `TheWallsShopItems` (
  `itemid` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `material` varchar(32) NOT NULL,
  `amount` int(11) NOT NULL,
  `ench1` varchar(32) DEFAULT NULL,
  `ench2` varchar(32) DEFAULT NULL,
  `type` varchar(4) NOT NULL,
  `slot` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `data` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `TheWallsShopItems`
--

INSERT INTO `TheWallsShopItems` (`itemid`, `name`, `material`, `amount`, `ench1`, `ench2`, `type`, `slot`, `price`, `data`) VALUES
(1, '§cEnchantment Table', 'ENCHANTMENT_TABLE', 1, NULL, NULL, 'temp', 0, 120, NULL),
(2, '§cTNT', 'TNT', 20, NULL, NULL, 'temp', 2, 60, NULL),
(3, '§8Flint and Steel', 'FLINT_AND_STEEL', 1, NULL, NULL, 'temp', 11, 60, NULL),
(4, '§fIron Sword', 'IRON_SWORD', 1, NULL, NULL, 'temp', 22, 100, NULL),
(5, '§8Anvil', 'ANVIL', 1, NULL, NULL, 'temp', 20, 120, NULL),
(6, '§eBottle o\' Enchanting', 'EXP_BOTTLE', 10, NULL, NULL, 'temp', 53, 60, NULL),
(7, '§7Stone Pickaxe', 'STONE_PICKAXE', 1, 'DIG_SPEED:1', 'DURABILITY:1', 'temp', 30, 140, NULL),
(8, '§7Stone Shovel', 'STONE_SPADE', 1, 'DIG_SPEED:1', 'DURABILITY:1', 'temp', 48, 140, NULL),
(9, '§7Stone Axe', 'STONE_AXE', 1, 'DIG_SPEED:1', 'DURABILITY:1', 'temp', 39, 140, NULL),
(10, '§6Bow', 'BOW', 1, 'ARROW_DAMAGE:1', NULL, 'temp', 9, 100, NULL),
(11, '§fArrow', 'ARROW', 16, NULL, NULL, 'temp', 10, 40, NULL),
(12, '§fIron Ingot', 'IRON_INGOT', 2, NULL, NULL, 'temp', 8, 40, NULL),
(13, '§eGolden Apple', 'GOLDEN_APPLE', 1, NULL, NULL, 'temp', 16, 120, 1),
(14, '§6Cooked Steak', 'COOKED_BEEF', 5, NULL, NULL, 'temp', 26, 40, NULL),
(15, '§fIron Spade', 'IRON_SPADE', 1, 'DIG_SPEED:1', NULL, 'temp', 47, 120, NULL),
(16, '§fIron Axe', 'IRON_AXE', 1, 'DIG_SPEED:1', NULL, 'temp', 38, 120, NULL),
(17, '§fIron Helmet', 'IRON_HELMET', 1, NULL, NULL, 'temp', 18, 90, NULL),
(18, '§fIron Chestplate', 'IRON_CHESTPLATE', 1, NULL, NULL, 'temp', 27, 270, NULL),
(19, '§fIron Leggins', 'IRON_LEGGINGS', 1, NULL, NULL, 'temp', 36, 225, NULL),
(20, '§fIron Boots', 'IRON_BOOTS', 1, NULL, NULL, 'temp', 45, 90, NULL),
(21, '§bDiamond', 'DIAMOND', 1, NULL, NULL, 'temp', 6, 50, NULL),
(22, '§2Emerald', 'EMERALD', 1, NULL, NULL, 'temp', 7, 60, NULL),
(23, '§fCake', 'CAKE', 5, NULL, NULL, 'temp', 17, 40, NULL),
(24, '§bDiamond Helmet', 'DIAMOND_HELMET', 1, NULL, NULL, 'temp', 19, 150, NULL),
(25, '§bDiamond Chestplate', 'DIAMOND_CHESTPLATE', 1, NULL, NULL, 'temp', 28, 320, NULL),
(26, '§bDiamond Leggins', 'DIAMOND_LEGGINGS', 1, NULL, NULL, 'temp', 37, 260, NULL),
(27, '§bDiamond Boots', 'DIAMOND_BOOTS', 1, NULL, NULL, 'temp', 46, 200, NULL),
(28, '§7Stone Sword', 'STONE_SWORD', 1, NULL, NULL, 'temp', 21, 60, NULL),
(29, '§cEnchantment Table', 'ENCHANTMENT_TABLE', 1, NULL, NULL, 'perm', 0, 3600, NULL),
(30, '§8Anvil', 'ANVIL', 1, NULL, NULL, 'perm', 1, 3600, NULL),
(31, '§6Wood', 'LOG', 16, NULL, NULL, 'perm', 2, 1250, NULL),
(32, '§cTNT', 'TNT', 10, NULL, NULL, 'perm', 3, 900, NULL),
(33, '§eBottle o\' Enchanting', 'EXP_BOTTLE', 10, NULL, NULL, 'perm', 4, 1800, NULL),
(34, '§6Cooked beef', 'COOKED_BEEF', 5, NULL, NULL, 'perm', 5, 1200, NULL),
(35, '§eGolden Apple', 'GOLDEN_APPLE', 1, NULL, NULL, 'perm', 6, 3600, 1),
(36, '§dEnchanted Book SHARP II', 'ENCHANTED_BOOK', 1, 'DAMAGE_ALL:2', NULL, 'perm', 8, 2250, NULL),
(37, '§2Emerald', 'EMERALD', 3, NULL, NULL, 'perm', 9, 1800, NULL),
(38, '§fIron Ingot', 'IRON_INGOT', 2, NULL, NULL, 'perm', 10, 1200, NULL),
(39, '§bDiamond', 'DIAMOND', 1, NULL, NULL, 'perm', 11, 1500, NULL),
(40, '§fSnowball', 'SNOW_BALL', 16, NULL, NULL, 'perm', 12, 900, NULL),
(41, '§3Water Bucket', 'WATER_BUCKET', 1, NULL, NULL, 'perm', 7, 600, NULL),
(42, '§fIron Helmet', 'IRON_HELMET', 1, NULL, NULL, 'perm', 18, 2700, NULL),
(43, '§fIron Chestplate', 'IRON_CHESTPLATE', 1, NULL, NULL, 'perm', 27, 8100, NULL),
(44, '§fIron Leggins', 'IRON_LEGGINGS', 1, NULL, NULL, 'perm', 36, 6750, NULL),
(45, '§bIron Boots', 'IRON_BOOTS', 1, NULL, NULL, 'perm', 45, 2700, NULL),
(46, '§6Bow', 'BOW', 1, NULL, NULL, 'perm', 19, 3000, NULL),
(47, '§fIron Sword', 'IRON_SWORD', 1, 'FIRE_ASPECT:2', NULL, 'perm', 28, 4500, NULL),
(48, '§6Iron Pickaxe', 'IRON_PICKAXE', 1, 'DIG_SPEED:1', NULL, 'perm', 37, 3600, NULL),
(49, '§7Stone Pickaxe', 'STONE_PICKAXE', 1, 'DIG_SPEED:1', NULL, 'perm', 46, 3000, NULL),
(50, '§3Potion of Healing', 'POTION', 1, NULL, NULL, 'perm', 13, 2250, 8197),
(51, '§6Villager Egg', 'MONSTER_EGG', 1, NULL, NULL, 'perm', 14, 1800, 120),
(52, '§fArrow', 'ARROW', 32, NULL, NULL, 'perm', 20, 2500, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `TheWallsShopItemsPurchased`
--

CREATE TABLE `TheWallsShopItemsPurchased` (
  `userid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL,
  `purchasetime` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `TheWallsStats`
--

CREATE TABLE `TheWallsStats` (
  `id` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `wins` int(11) NOT NULL,
  `lose` int(11) NOT NULL,
  `kills` int(11) NOT NULL,
  `deaths` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `Messages`
--
ALTER TABLE `Messages`
  ADD PRIMARY KEY (`msgid`),
  ADD KEY `minigame` (`minigame`);

--
-- Indexes for table `Players`
--
ALTER TABLE `Players`
  ADD PRIMARY KEY (`userid`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `username_2` (`username`),
  ADD KEY `userid` (`userid`),
  ADD KEY `username_3` (`username`);

--
-- Indexes for table `Settings`
--
ALTER TABLE `Settings`
  ADD PRIMARY KEY (`settingsid`),
  ADD UNIQUE KEY `path` (`path`);

--
-- Indexes for table `TheWallsMoney`
--
ALTER TABLE `TheWallsMoney`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userid` (`userid`),
  ADD KEY `userid_2` (`userid`);

--
-- Indexes for table `TheWallsShopItems`
--
ALTER TABLE `TheWallsShopItems`
  ADD PRIMARY KEY (`itemid`),
  ADD KEY `itemid` (`itemid`);

--
-- Indexes for table `TheWallsShopItemsPurchased`
--
ALTER TABLE `TheWallsShopItemsPurchased`
  ADD PRIMARY KEY (`userid`,`itemid`),
  ADD KEY `userid` (`userid`);

--
-- Indexes for table `TheWallsStats`
--
ALTER TABLE `TheWallsStats`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userid` (`userid`),
  ADD KEY `userid_2` (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `Messages`
--
ALTER TABLE `Messages`
  MODIFY `msgid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=383;
--
-- AUTO_INCREMENT dla tabeli `Players`
--
ALTER TABLE `Players`
  MODIFY `userid` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `Settings`
--
ALTER TABLE `Settings`
  MODIFY `settingsid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=143;
--
-- AUTO_INCREMENT dla tabeli `TheWallsMoney`
--
ALTER TABLE `TheWallsMoney`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `TheWallsShopItems`
--
ALTER TABLE `TheWallsShopItems`
  MODIFY `itemid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;
--
-- AUTO_INCREMENT dla tabeli `TheWallsStats`
--
ALTER TABLE `TheWallsStats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
