package club.mher.compass.data;

import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MessagesData {

    public final static String PATH = "addons.compass.";

    public MessagesData() {
        setupMessages();
    }

    public void setupMessages() {
        for (Language l : Language.getLanguages()) {
            YamlConfiguration yml = l.getYml();
            switch (l.getIso()) {
                // By Sxhqil (Persian)
                case "fa":
                    yml.addDefault(NOT_ALL_BEDS_DESTROYED, "&cHich Kodoom Az Bed Haye Doshman Kharab Nashode!");
                    yml.addDefault(PURCHASED, "&cShoma In Ghabeliat Ro Az Dast Midid Age Bemirid Va Dige Nemitoonid Track Konid!");
                    yml.addDefault(NOT_ENOUGH_RESOURCE, "&cShoma Resource Haye Kafy Baraye In Kar Ro Nadarid!");
                    yml.addDefault(ALREADY_TRACKING, "&cShoma Az Ghabl In Team Ro Track Kardid!");
                    yml.addDefault(STATUS_NOT_ENOUGH, "&cShoma Resource Kafy Nadarid!");
                    yml.addDefault(STATUS_LOCKED, "&cVaqty Baz Mishe Ke Hameye Bed Haye Harif Hatoon Kharab She");
                    yml.addDefault(STATUS_UNLOCKED, "&eClick Kon Ta Bekhary!");
                    yml.addDefault(ACTION_BAR_TRACKING, "&fTracking: {teamColor}&l{target} &f- Distance: &a&l{distance}m");
                    yml.addDefault(TEAM_MESSAGE_FORMAT, "&a&lTEAM > &7{player}: {message}");
                    saveItem(yml, MainConfig.COMPASS_ITEM, "&aCompass &7(Rast Click)");
                    saveItem(yml, MainConfig.TRACKER_SHOP, "&aForooshgahe Tracker ", "&7Yek Tracker Bekhar Ta","&7Az Compasset Baraye Track Kardane","&7Harif Estefade Kon","&7In Ghabeliat Ta Vaqty Hast Ke Bemiri");
                    yml.addDefault(MAIN_MENU_TITLE, "&8Tracker & Communications");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER, "&aForooshgahe Tracker ", "&7Yek Tracker Bekhar Ta","&7Az Compasset Baraye Track Kardane","&7Harif Estefade Kon","&7In Ghabeliat Ta Vaqty Hast Ke Bemiri", "", "&eClick Kon Ta Baz She!");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER_TEAM, "&aForooshgahe Tracker ", "&7Yek Tracker Bekhar Ta","&7Az Compasset Baraye Track Kardane","&7Harif Estefade Kon","&7In Ghabeliat Ta Vaqty Hast Ke Bemiri", "", "&eClick Kon Ta Baz She!");
                    saveItem(yml, MainConfig.MAIN_MENU_COMMUNICATIONS, "&aQuick Communications", "&7Yek Payame Moshkhas Shode", "&7Baraye Ham Teami Yat Befres!", "", "&eClick Kon Ta Baz She!");
                    yml.addDefault(TRACKER_MENU_TITLE, "&8Kharide Enemy Tracker");
                    saveItem(yml, MainConfig.TRACKER_MENU_TEAM_ITEM, "&cTrack Kardane Teame {team}", "&7Yek Tracker Bekhar Ta","&7Az Compasset Baraye Track Kardane","&7Harif Estefade Kon","&7In Ghabeliat Ta Vaqty Hast Ke Bemiri", "", "&7Cost: &22 Emeralds", "", "{status}");
                    saveItem(yml, MainConfig.TRACKER_MENU_BACK_ITEM, "&aBargasht", "&7Be Tracker & Communication");
                    yml.addDefault(COMMUNICATIONS_MENU_TITLE, "&8Ertebate Sarry!");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_BACK, "&aBargasht", "&7Be Tracker & Communication");
                    if (yml.getString(PATH + MainConfig.COMMUNICATIONS_MENU_ITEMS) == null) {
                        saveCommunicationItem(yml, "1", "&aSalam ( ?????????)/!", "&aSalam ( ?????????)/!", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "2", "&aDaram Be Base Barmigardam", "&aDaram Be Base Barmigardam", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "3", "&aDaram Defa Mikonam!", "&aDaram Defa Mikonam!", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "4", "&aDaram Rush Midam Be Teame {team}", "&aDaram Rush Midam!", "&7Shoma In Ghabeliat Ro Darid Ke", "&7Yek Team Ro Entekhab Konid", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "5", "&aDaram {resource} &aJam Mikonam", "&aDaram Resource Jam Mikonam!", "&7Shoma In Ghabeliat Ro Darid Ke", "&7Resource Ro Entekhab Konid", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "6", "&7{resource} &aGereftam", "&aResource Gereftam!", "&7Shoma In Ghabeliat Ro Darid Ke", "&7Resource Ro Entekhab Konid", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "7", "&aMamnoon", "&aMamnoon!", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "8", "&aBargard Be Base", "&aBargard Be Base!", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "9", "&aLotfan Defa Kon!", "&aLotfan Defa Kon!", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "10", "&aBerim Be {team} &aHamle Konim", "&aBerim Rush Bedim!", "&7Shoma In Ghabeliat Ro Darid Ke", "&7Yek Team Ro Entekhab Konid", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "11", "&aMa Be {resource} &aNiaz Darim.", "&aBe Resource Niaz Darim!", "&7Shoma In Ghabeliat Ro Darid Ke", "&7Resource Ro Entekhab Konid", "", "&eClick Kon Ta Befresty!");
                        saveCommunicationItem(yml, "12", "&aYe Player Dare Miad!", "&aPlayer Dare Miad!!", "", "&eClick Kon Ta Befresty!");
                    }
                    yml.addDefault(COMMUNICATIONS_MENU_LORE, Arrays.asList("&7Click Kon Ta Payam Ro Befresty: '{message}&7'", "&7Be Hamtimiat!", "", "&eClick Kon Ta Befresty!"));
                    yml.addDefault(COMMUNICATIONS_MENU_TEAMS_TITLE, "&8Select an option:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_TEAMS + ".back-item", "&aBargasht", "&7Be Quick Communications");
                    yml.addDefault(COMMUNICATIONS_MENU_RESOURCES_TITLE, "&8Select an option:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".back-item", "&aBargasht", "&7Be Quick Communications");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".iron", "&f&lIRON");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".gold", "&6&lGOLD");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".diamond", "&b&lDIAMOND");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".emerald", "&2&lEMERALD");
                case "es":
                    yml.addDefault(NOT_ALL_BEDS_DESTROYED, "&cNo todos los enemigos tienen la cama destruida!");
                    yml.addDefault(PURCHASED, "&cPerder??s la habilidad de rastrear a este equipo cuando mueras!");
                    yml.addDefault(NOT_ENOUGH_RESOURCE, "&cNo tienes suficientes recursos!");
                    yml.addDefault(ALREADY_TRACKING, "&cYa estas rastreando a este equipo!");
                    yml.addDefault(STATUS_NOT_ENOUGH, "&cNo tienes suficientes recursos!");
                    yml.addDefault(STATUS_LOCKED, "&cSe desbloqueara cuando todas las camas enemigas esten destruidas!");
                    yml.addDefault(STATUS_UNLOCKED, "&eClick para comprar!");
                    yml.addDefault(ACTION_BAR_TRACKING, "&fRastreando a: {teamColor}&l{target} &f- Distancia: &a&l{distance}m");
                    yml.addDefault(TEAM_MESSAGE_FORMAT, "&a&lTEAM > &7{player}: {message}");
                    saveItem(yml, MainConfig.COMPASS_ITEM, "&aBrujula &7(Click Derecho)");
                    saveItem(yml, MainConfig.TRACKER_SHOP, "&aTracker Shop", "&7Purchase tracking update for","&7your compass which will track","&7each player on a specific team","&7until you die.");
                    yml.addDefault(MAIN_MENU_TITLE, "&7Comunicaci??n r??pida y rastreador");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER, "&aTienda de rastreadores", "&7Compra la habilidad de rastrear", "&7para tu br??jula", "&7rastreando a cada jugador en un", "&7equipo espec??fico hasta", "&7que mueras.", "", "&eClick para abrir!");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER_TEAM, "&aTienda de rastreadores", "&7Compra la habilidad de rastrear", "&7para tu br??jula", "&7rastreando a cada jugador en un", "&7equipo espec??fico hasta", "&7que mueras.", "", "&eClick para abrir!");
                    saveItem(yml, MainConfig.MAIN_MENU_COMMUNICATIONS, "&aComunicaci??n r??pida", "&7Env??a un chat r??pido a", "&7tu equipo!", "", "&eClick para abrir!");
                    yml.addDefault(TRACKER_MENU_TITLE, "&8Comprar rastreador de enemigos");
                    saveItem(yml, MainConfig.TRACKER_MENU_TEAM_ITEM, "&cRastrear equipo {team}", "&7Compra la habilidad de rastrear", "&7para tu br??jula", "&7rastreando a cada jugador en un", "&7equipo espec??fico hasta", "&7que mueras.", "", "&7Precio: &22 Emeralds", "", "{status}");
                    saveItem(yml, MainConfig.TRACKER_MENU_BACK_ITEM, "&aAtr??s", "&7A Comunicaci??n r??pida y rastreador");
                    yml.addDefault(COMMUNICATIONS_MENU_TITLE, "&8Comunicaci??n rap??da");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_BACK, "&aAtr??s", "&7A Comunicaci??n r??pida y rastreador");
                    if (yml.getString(PATH + MainConfig.COMMUNICATIONS_MENU_ITEMS) == null) {
                        saveCommunicationItem(yml, "1", "&aHola ( ?????????)/!", "&aHola ( ?????????)/!", "", "&eClick apra enviar!");
                        saveCommunicationItem(yml, "2", "&aEstoy volviendo a la base!", "&aEstoy volviendo a la base!", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "3", "&aEstoy defendiendo!", "&aEstoy defendiendo!", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "4", "&aEstoy atacando a {team}", "&aEstoy atacando!", "&7Tienes que seleccionar", "&7el equipo", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "5", "&aEstoy recolectando {resource}", "&aEstoy recolectando recursos!", "&7Tienes que seleccionar", "&7el material", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "6", "&aTengo {resource}", "&aTengo materiales!", "&7Tienes que seleccionar", "&7el material", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "7", "&aGracias!", "&aGracias!", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "8", "&aVuelve a la base", "&aVuelve a la base!", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "9", "&aPorfavor, defiende!", "&aPorfavor, defiende!", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "10", "&aVamos a atacar a {team}", "&aVamos a atacar!", "&7Tienes que seleccionar", "&7el equipo", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "11", "&aNecesitamos {resource}", "&aNecesitamos materiales!", "&7Tienes que seleccionar", "&7el material", "", "&eClick para enviar!");
                        saveCommunicationItem(yml, "12", "&aEsta entrando un jugador!", "&aEsta entrando un jugador!", "", "&eClick para enviar!");
                    }
                    yml.addDefault(COMMUNICATIONS_MENU_LORE, new String[]{"&7Click para enviar: '{message}&7'", "&7a tu equipo!", "", "&eClick para enviar!"});
                    yml.addDefault(COMMUNICATIONS_MENU_TEAMS_TITLE, "&8Select an option:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_TEAMS + ".back-item", "&aVolver", "&7A comunicacion r??pida");
                    yml.addDefault(COMMUNICATIONS_MENU_RESOURCES_TITLE, "&8Selecciona una accion:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".back-item", "&aVolver", "&7A comunicacion r??pida");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".iron", "&f&lHIERRO");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".gold", "&6&lORO");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".diamond", "&b&lDIAMANTES");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".emerald", "&2&lESMERALDAS");
                    break;
                case "ru":
                    yml.addDefault(NOT_ALL_BEDS_DESTROYED, "&c?????? ???? ?????? ?????????????????? ?????????????? ????????????????????!");
                    yml.addDefault(PURCHASED, "&c???? ?????????????????? ?????????????????????? ?????????????????????? ?????? ??????????????, ?????????? ????????????!");
                    yml.addDefault(NOT_ENOUGH_RESOURCE, "&c?? ?????? ???????????????????????? ????????????????!");
                    yml.addDefault(ALREADY_TRACKING, "&c???? ?????? ???????????????????????? ?????? ??????????????!");
                    yml.addDefault(STATUS_NOT_ENOUGH, "&c?? ?????? ???????????????????????? ????????????????!");
                    yml.addDefault(STATUS_LOCKED, "&c??????????????????????, ?????????? ?????? ?????????????????? ?????????????? ????????????????????!");
                    yml.addDefault(STATUS_UNLOCKED, "&e??????????????, ?????????? ????????????!");
                    yml.addDefault(ACTION_BAR_TRACKING, "&f????????????????????????: {teamColor}&l{target} &f- ??????????????????: &a&l{distance}m");
                    yml.addDefault(TEAM_MESSAGE_FORMAT, "&a&l?????????????? > &7{player}: {message}");
                    saveItem(yml, MainConfig.COMPASS_ITEM, "&a???????????? &7(??????)");
                    saveItem(yml, MainConfig.TRACKER_SHOP, "&aTracker Shop", "&7Purchase tracking update for","&7your compass which will track","&7each player on a specific team","&7until you die.");
                    yml.addDefault(MAIN_MENU_TITLE, "&8???????????? ?? ????????????????????????");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER, "&a?????????????? ????????????????", "&7???????????????????? ???????????????????????? ??????????????", "&7?????? ???????????? ??????????????, ?????????????? ??????????", "&7?????????????????????? ?????????????? ???????????? ????", "&7???????????????????? ??????????????, ???????? ????" , "&7????????.", "", "&e??????????????, ?????????? ??????????????!");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER_TEAM, "&a?????????????? ????????????????", "&7???????????????????? ???????????????????????? ??????????????", "&7?????? ???????????? ??????????????, ?????????????? ??????????", "&7?????????????????????? ?????????????? ???????????? ????", "&7???????????????????? ??????????????, ???????? ????" , "&7????????.", "", "&e??????????????, ?????????? ??????????????!");
                    saveItem(yml, MainConfig.MAIN_MENU_COMMUNICATIONS, "&a?????????????? ??????????????", "&7?????????????????? ???????????????????? ??????", "&7?????????????????? ?????????? ??????????????????", "&7???? ??????????????!", "", "&e??????????????, ?????????? ??????????????!");
                    yml.addDefault(TRACKER_MENU_TITLE, "&8?????????????? ???????? ????????????");
                    saveItem(yml, MainConfig.TRACKER_MENU_TEAM_ITEM, "&c?????????????? ?????????? {team}", "&7???????????????????? ???????????????????????? ??????????????" , "&7?????? ???????????? ??????????????, ?????????????? ??????????" , "&7?????????????????????? ?????????????? ???????????? ????", "&7???????????????????? ??????????????, ???????? ????", "&7????????.", "", "&7??????????????: &22 ????????????????", "", "{status}");
                    saveItem(yml, MainConfig.TRACKER_MENU_BACK_ITEM, "&a?????????????????? ??????????", "&7?? ?????????????? ?? ??????????");
                    yml.addDefault(COMMUNICATIONS_MENU_TITLE, "&8?????????????? ??????????????");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_BACK, "&a?????????????????? ??????????", "&7?? ?????????????? ?? ??????????");
                    if (yml.getString(PATH + MainConfig.COMMUNICATIONS_MENU_ITEMS) == null) {
                        saveCommunicationItem(yml, "1", "&a???????????? ( ?????????)/!", "&a???????????? ( ?????????)/!", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "2", "&a?? ?????????????????????? ???? ????????!", "&a?? ?????????????????????? ???? ????????!", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "3", "&a?? ??????????????????!", "&a?? ??????????????????!", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "4", "&a?? ???????????? {team}", "&a?? ????????????!", "&7???? ?????????????? ??????????????", "&7??????????????", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "5", "&a?? ?????????????? {resource}", "&a?????????????? ??????????????!", "&7???? ?????????????? ??????????????", "&7????????????", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "6", "&a?? ???????? ???????? {resource}", "&a?? ???????? ???????? ??????????????!", "&7???? ?????????????? ??????????????", "&7????????????", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "7", "&a??????????????", "&a??????????????", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "8", "&a?????????????????????? ???? ????????!", "&a?????????????????????? ???? ????????!", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "9", "&a????????????????????, ??????????????????????!", "&a????????????????????, ??????????????????????!", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "10", "&a?????????????? {team}", "&a??????????????!", "&7???? ?????????????? ??????????????", "&7??????????????", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "11", "&a?????? ?????????? {resource}", "&a?????? ?????????? ??????????????!", "&7???? ?????????????? ??????????????", "&7????????????", "", "&e??????????????, ?????????? ??????????????????!");
                        saveCommunicationItem(yml, "12", "&a???????????????? ??????????!", "&a???????????????? ??????????!!", "", "&e??????????????, ?????????? ??????????????????!");
                    }
                    yml.addDefault(COMMUNICATIONS_MENU_LORE, new String[]{"&7??????????????, ?????????? ??????????????????", "&7??????????????????: '{message}&7'", "&7?????????? ?????????????????? ???? ??????????????!", "", "&e??????????????, ?????????? ??????????????????!"});
                    yml.addDefault(COMMUNICATIONS_MENU_TEAMS_TITLE, "&8???????????????? ??????????????:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_TEAMS + ".back-item", "&a?????????????????? ??????????" , "&7?? ?????????????? ??????????!" );
                    yml.addDefault(COMMUNICATIONS_MENU_RESOURCES_TITLE, "&8???????????????? ??????????????:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".back-item", "&a?????????????????? ??????????", "&7?? ?????????????? ??????????!");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".iron", "&f&l????????");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".gold", "&6&l????????????");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".diamond", "&b&l??????????");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".emerald", "&2&l??????????????");
                    break;

                // By @SAvselgafg415 (Polish)
                case "pl":
                    yml.addDefault(NOT_ALL_BEDS_DESTROYED, "&cWszystkie ??????ka nie zosta??y zniszczone!");
                    yml.addDefault(PURCHASED, "&cJe??li umrzesz stracisz namierzanie!");
                    yml.addDefault(NOT_ENOUGH_RESOURCE, "&cMasz za malo surowc??w!");
                    yml.addDefault(ALREADY_TRACKING, "&cNamierzasz ju?? t?? dru??yn??!");
                    yml.addDefault(STATUS_NOT_ENOUGH, "&cNie sta?? ciebie!");
                    yml.addDefault(STATUS_LOCKED, "&cB??dzi??sz m??g?? tego u??y??, je??li wszystkie ??????ka zostan?? zniszczone!");
                    yml.addDefault(STATUS_UNLOCKED, "&eKliknij, aby kupi??!");
                    yml.addDefault(ACTION_BAR_TRACKING, "&fNamierzanie: {teamColor}&l{target} &f- Dystans: &a&l{distance}m");
                    yml.addDefault(TEAM_MESSAGE_FORMAT, "&a&lDRU??YNA > &7{player}: {message}");
                    saveItem(yml, MainConfig.COMPASS_ITEM, "&aKompas &7(PRM)");
                    saveItem(yml, MainConfig.TRACKER_SHOP, "&aTracker Shop", "&7Purchase tracking update for","&7your compass which will track","&7each player on a specific team","&7until you die.");
                    yml.addDefault(MAIN_MENU_TITLE, "&8Namierzanie i komunikacja");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER, "&aNamierzanie", "&7Kupuj??c namierzanie dru??yny", "&7jeste?? przekonany, ??e", "&7dojdziesz do wroga", "&7Stracisz namierzanie", "&7je??li umrzesz.", "", "&eKliknij, aby otworzyc!");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER_TEAM, "&aNamierzanie", "&7Kupuj??c namierzanie dru??yny", "&7jeste?? przekonany, ??e", "&7dojdziesz do wroga", "&7Stracisz namierzanie", "&7je??li umrzesz.", "", "&eKliknij, aby otworzyc!");
                    saveItem(yml, MainConfig.MAIN_MENU_COMMUNICATIONS, "&aSzybka komunikacja", "&7Kontaktuj si?? ze", "&7swoj?? dru??ym??!", "", "&eKliknik, aby otworzy??!");
                    yml.addDefault(TRACKER_MENU_TITLE, "&8Kup namierzanie");
                    saveItem(yml, MainConfig.TRACKER_MENU_TEAM_ITEM, "&cNamierzaj dru??yn?? {team}", "&7Kupuj??c namierzanie dru??yny", "&7jeste?? przekonany, ??e", "&7dojdziesz do wroga.", "&7Wszystkie ??????ka musz?? zosta??", "&7zniszczone, aby u??yc namierzania.", "", "&7Cena: &22 Emeraldy", "", "{status}");
                    saveItem(yml, MainConfig.TRACKER_MENU_BACK_ITEM, "&aPowrot", "&7Powraca ciebie do g????wnej strony");
                    yml.addDefault(COMMUNICATIONS_MENU_TITLE, "&aSzybka komunikacja");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_BACK, "&aPowrot", "&7Powraca ciebie do g????wnej strony");
                    if (yml.getString(PATH + MainConfig.COMMUNICATIONS_MENU_ITEMS) == null) {
                        saveCommunicationItem(yml, "1", "&aCze???? ( ?????????)/!", "&aCze???? ( ?????????)/!", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "2", "&aWracam do bazy!", "&aWracam do bazy!", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "3", "&aBroni??!", "&aBroni??!", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "4", "&aAtakuj?? dru??yn?? {team}", "&aAtakuj??!", "&7B??dziesz m??g?? wybra??", "&7dru??yn??.", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "5", "&aZbieram {resource}", "&aZbieram surowce!", "&7B??dziesz m??g?? wybra??", "&7zas??b", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "6", "&aPosiadam {resource}", "&aMam surowce!", "&7B??dziesz m??g?? wybra??", "&7zas??b", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "7", "&aDzi??kuj??", "&aDzi??kuj??", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "8", "&aWracaj do bazy", "&aWracaj do bazy!", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "9", "&aProsz??, bro?? baz??", "&aProsz??, bro?? baz??!", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "10", "&aZaatakujmy {team}", "&aZaatakujmy!", "&7B??dziesz m??g?? wybra??", "&7dru??yn??.", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "11", "&aPotrzebujemy {resource}", "&aPotrzebne nam s??!", "&7B??dziesz m??g?? wybra??", "&7zas??b", "", "&eKliknij, aby wyslac!");
                        saveCommunicationItem(yml, "12", "&aWrog sie zbliza!", "&aWrog sie zbliza!!", "", "&eKliknij, aby wyslac!");
                    }
                    yml.addDefault(COMMUNICATIONS_MENU_LORE, new String[]{"&7Kliknij, aby wyslac wiadomosc: '{message}&7'", "&7do twojej druzyny!", "", "&eKliknij, aby wyslac!"});
                    yml.addDefault(COMMUNICATIONS_MENU_TEAMS_TITLE, "&8Wybierz opcj??:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_TEAMS + ".back-item", "&aPowrot", "&7Powraca ciebie do szybkiej komunimacji");
                    yml.addDefault(COMMUNICATIONS_MENU_RESOURCES_TITLE, "&8Wybierz opcj??:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".back-item", "&aPowrot", "&7Powraca ciebie do szybkiej komunimacji");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".iron", "&f&lZELAZO");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".gold", "&6&lZLOTO");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".diamond", "&b&lDIAMENT");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".emerald", "&2&lSZMARAGD");
                    break;

                // By @Gradin98 (Romain)
                case "ro":
                    yml.addDefault(NOT_ALL_BEDS_DESTROYED, "&cNu toate paturile au fost distruse inca!");
                    yml.addDefault(PURCHASED, "&cO sa pierzi abilitatea de a urmarii aceasta echipa cand vei murii!");
                    yml.addDefault(NOT_ENOUGH_RESOURCE, "&cNu ai destule resurse!");
                    yml.addDefault(ALREADY_TRACKING, "&cDeja urmaresti aceasta echipa!");
                    yml.addDefault(STATUS_NOT_ENOUGH, "&cNu ai destule resurse!");
                    yml.addDefault(STATUS_LOCKED, "&cSe va debloca cand toate paturile inamicilor vor fi distruse!");
                    yml.addDefault(STATUS_UNLOCKED, "&eClick pentru a cumpara!");
                    yml.addDefault(ACTION_BAR_TRACKING, "&fUrmaresti: {teamColor}&l{target} &f- Distanta: &a&l{distance}m");
                    yml.addDefault(TEAM_MESSAGE_FORMAT, "&a&lEchipa > &7{player}: {message}");
                    saveItem(yml, MainConfig.COMPASS_ITEM, "&aCompas &7(Click Dreapta)");
                    saveItem(yml, MainConfig.TRACKER_SHOP, "&aTracker Shop", "&7Purchase tracking update for","&7your compass which will track","&7each player on a specific team","&7until you die.");
                    yml.addDefault(MAIN_MENU_TITLE, "&8Tracker & Communications");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER, "&aMagazin de Trackere", "&7Cumpara tracker upgrades", "&7pentru compasul care va", "&7urmarii jucatorii dintr-o", "&7echipa specifica pana cand tu vei", "&7murii.", "", "&eClick sa deschizi!");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER_TEAM, "&aMagazin de Trackere", "&7Cumpara tracker upgrades", "&7pentru compasul care va", "&7urmarii jucatorii dintr-o", "&7echipa specifica pana cand tu vei", "&7murii.", "", "&eClick sa deschizi!");
                    saveItem(yml, MainConfig.MAIN_MENU_COMMUNICATIONS, "&aComunicare rapida", "&7Trimite mesaje evidentiate", "&7catre echipa ta!", "", "&eClick sa deschizi!");
                    yml.addDefault(TRACKER_MENU_TITLE, "&8Cumpara tracker inamic");
                    saveItem(yml, MainConfig.TRACKER_MENU_TEAM_ITEM, "&cUrmareste echipa {team}", "&7Cumpara tracker upgrades", "&7pentru compasul care va", "&7urmarii jucatorii dintr-o", "&7echipa specifica pana cand tu vei", "&7murii.", "", "&7Cost: &22 Emeralde", "", "{status}");
                    saveItem(yml, MainConfig.TRACKER_MENU_BACK_ITEM, "&aDu-te inapoi", "&7To Tracker & Communication");
                    yml.addDefault(COMMUNICATIONS_MENU_TITLE, "&8Comunicare Rapida");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_BACK, "&aDu-te inapoi", "&7To Tracker & Communication");
                    if (yml.getString(PATH + MainConfig.COMMUNICATIONS_MENU_ITEMS) == null) {
                        saveCommunicationItem(yml, "1", "&aSalut ( ?????????)/!", "&aSalut ( ?????????)/!", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "2", "&aRevin catre baza", "&aRevin catre baza!", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "3", "&aSunt in aparare", "&aSunt in aparare!", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "4", "&aAtac echipa {team}", "&aSunt in atac!", "&7Vei avea posibilitatea de a", "&7selecta echipa", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "5", "&aColectez {resource}", "&aColectez resurse!", "&7Vei avea posibilitatea de a", "&7selecta resursa", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "6", "&aAm {resource}", "&aAm resursa!", "&7Vei avea posibilitatea de a", "&7selecta resursa", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "7", "&aMultumesc", "&aMultumesc!", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "8", "&aDu-te inapoi in baza", "&aDu-te inapoi in baza", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "9", "&aTe rog apara!", "&aTe rog apara!", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "10", "&aSa atacam echipa {team}", "&aSa atacam!", "&7Vei avea posibilitatea de a", "&7selecta echipa", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "11", "&aAvem nevoie de {resource}", "&aAvem nevoie de resurse!", "&7Vei avea posibilitatea de a", "&7selecta resursa", "", "&eClick sa trimiti!");
                        saveCommunicationItem(yml, "12", "&aVine un player!", "&aVine un player!!", "", "&eClick sa trimiti!");
                    }
                    yml.addDefault(COMMUNICATIONS_MENU_LORE, new String[]{"&7Click sa trimiti mesajul: '{message}&7'", "&7catre echipa ta", "", "&eClick sa trimiti!"});
                    yml.addDefault(COMMUNICATIONS_MENU_TEAMS_TITLE, "&8Selecteaza o optiunea:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_TEAMS + ".back-item", "&aDu-te inapoi", "&7Catre comunicare rapida");
                    yml.addDefault(COMMUNICATIONS_MENU_RESOURCES_TITLE, "&8Selecteaza o optiune:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".back-item", "&aDu-te inapoi", "&7Catre comunicare rapida");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".iron", "&f&lFIER");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".gold", "&6&lAUR");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".diamond", "&b&lDIAMANT");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".emerald", "&2&lEMERALD");
                    break;

                // By @Yuri2005 (Italian)
                case "it":
                    yml.addDefault(NOT_ALL_BEDS_DESTROYED, "&cNon sono distrutti tutti i letti!");
                    yml.addDefault(PURCHASED, "&cPerderai l'abilit?? di rintracciare i giocatori alla morte!");
                    yml.addDefault(NOT_ENOUGH_RESOURCE, "&cNon hai abbastanza risorse!");
                    yml.addDefault(ALREADY_TRACKING, "&cStai gi?? rintracciando un team!");
                    yml.addDefault(STATUS_NOT_ENOUGH, "&cNon ha abbastanza risorse!");
                    yml.addDefault(STATUS_LOCKED, "&cSi sbloccher?? quando tutti i letti sono distrutti!");
                    yml.addDefault(STATUS_UNLOCKED, "&eClicca per acquistare!");
                    yml.addDefault(ACTION_BAR_TRACKING, "&fTracking: {teamColor}&l{target} &f- Distanza: &a&l{distance}m");
                    yml.addDefault(TEAM_MESSAGE_FORMAT, "&a&lTEAM > &7{player}: {message}");
                    saveItem(yml, MainConfig.COMPASS_ITEM, "&aBussola &7(Click destro)");
                    saveItem(yml, MainConfig.TRACKER_SHOP, "&aTracker Shop", "&7Purchase tracking update for","&7your compass which will track","&7each player on a specific team","&7until you die.");
                    yml.addDefault(MAIN_MENU_TITLE, "&8Tracker & Comunicazioni");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER, "&aAcquista il tracker", "&7Compra l'aggiornamento Tracker", "&7che ci penser?? la tua bussola a farlo", "&7Rintraccia ogni giocatore in una", "&7squadra specifica fino alla", "&7tua morte!.", "", "&eClicca per aprire!");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER_TEAM, "&aAcquista il tracker", "&7Compra l'aggiornamento Tracker", "&7che ci penser?? la tua bussola a farlo", "&7Rintraccia ogni giocatore in una", "&7squadra specifica fino alla", "&7tua morte!.", "", "&eClicca per aprire!");
                    saveItem(yml, MainConfig.MAIN_MENU_COMMUNICATIONS, "&aComunicazioni veloci", "&7Invia una chat evidenziata", "&7messaggi ai tuoi compagni di squadra!", "", "&eClicca per aprire!");
                    yml.addDefault(TRACKER_MENU_TITLE, "&8Compra il player Tracker");
                    saveItem(yml, MainConfig.TRACKER_MENU_TEAM_ITEM, "&cRintraccia la squadra {team}", "&7Compra l'aggiornamento Tracker", "&7che ci penser?? la tua bussola a farlo", "&7Rintraccia ogni giocatore in una", "&7squadra specifica fino alla", "&7tua morte!.", "", "&7Costo: &22 Smeraldi", "", "{status}");
                    saveItem(yml, MainConfig.TRACKER_MENU_BACK_ITEM, "&aTorna indietro", "&7To Tracker & Comunicazioni");
                    yml.addDefault(COMMUNICATIONS_MENU_TITLE, "&8Comunicazioni veloci");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_BACK, "&aTorna indietro", "&7To Tracker & Comunicazioni");
                    if (yml.getString(PATH + MainConfig.COMMUNICATIONS_MENU_ITEMS) == null) {
                        saveCommunicationItem(yml, "1", "&aCiao ( ?????????)/!", "&aCiao ( ?????????)/!", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "2", "&aSto tornando alla base!", "&aSto tornando alla base!", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "3", "&aSto difendendo", "&aSto difendendo!", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "4", "&aSto attaccando la squadra {team}", "&aSto attaccando!", "&7potrai selezionare", "&7la squadra", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "5", "&aSto raccogliendo {resource}", "&aSto raccogliendo risorse!", "&7potrai selezionare", "&7la risorsa", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "6", "&aHo {resource}", "&aHo risorse!", "&7potrai selezionare", "&7la risorsa", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "7", "&aGrazie", "&aGrazie!", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "8", "&aTorniamo in base", "&aTorniamo in base!", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "9", "&aDifendete per favore", "&aPer favore difendete!", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "10", "&aAttacchiamo la squadra {team}", "&aAttacchiamo!", "&7potrai selezionare", "&7la squadra", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "11", "&aCi serve {resource}", "&aCi servono risorse!", "&7potrai selezionare", "&7la risorsa", "", "&eClicca per inviare!");
                        saveCommunicationItem(yml, "12", "&aSta arrivando un giocatore!", "&aSta arrivando un giocatore!", "", "&eClicca per inviare!");
                    }
                    yml.addDefault(COMMUNICATIONS_MENU_LORE, new String[]{"&7Clicca per inviare un messaggio: '{message}&7'", "&7ai tuoi compagni di squadra!", "", "&eClicca per inviare!"});
                    yml.addDefault(COMMUNICATIONS_MENU_TEAMS_TITLE, "&8Scegli un'opzione:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_TEAMS + ".back-item", "&aIndietro!", "&7Comunicazioni rapide");
                    yml.addDefault(COMMUNICATIONS_MENU_RESOURCES_TITLE, "&8Scegli un'opzione:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".back-item", "&aTorna indietro!", "&7Comunicazioni rapide");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".iron", "&f&lFERRO");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".gold", "&6&lORO");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".diamond", "&b&lDIAMANTE");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".emerald", "&2&lSMERALDO");
                    break;

                default:
                    yml.addDefault(NOT_ALL_BEDS_DESTROYED, "&cNot all enemy beds are destroyed yet!");
                    yml.addDefault(PURCHASED, "&cYou will lose the ability to track this team when you die!");
                    yml.addDefault(NOT_ENOUGH_RESOURCE, "&cYou don't have enough resources!");
                    yml.addDefault(ALREADY_TRACKING, "&cYou are already tracking this team!");
                    yml.addDefault(STATUS_NOT_ENOUGH, "&cYou don't have enough resource!");
                    yml.addDefault(STATUS_LOCKED, "&cUnlocks when all enemy beds are destroyed!");
                    yml.addDefault(STATUS_UNLOCKED, "&eClick to purchase!");
                    yml.addDefault(ACTION_BAR_TRACKING, "&fTracking: {teamColor}&l{target} &f- Distance: &a&l{distance}m");
                    yml.addDefault(TEAM_MESSAGE_FORMAT, "&a&lTEAM > &7{player}: {message}");
                    saveItem(yml, MainConfig.COMPASS_ITEM, "&aCompass &7(Right Click)");
                    saveItem(yml, MainConfig.TRACKER_SHOP, "&aTracker Shop", "&7Purchase tracking update for","&7your compass which will track","&7each player on a specific team","&7until you die.");
                    yml.addDefault(MAIN_MENU_TITLE, "&8Tracker & Communications");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER, "&aTracker Shop", "&7Purchase tracking upgrade", "&7for your compass which will", "&7track each player on a", "&7specific team until you", "&7die.", "", "&eClick to open!");
                    saveItem(yml, MainConfig.MAIN_MENU_TRACKER_TEAM, "&aTracker Shop", "&7Purchase tracking upgrade", "&7for your compass which will", "&7track each player on a", "&7specific team until you", "&7die.", "", "&eClick to open!");
                    saveItem(yml, MainConfig.MAIN_MENU_COMMUNICATIONS, "&aQuick Communications", "&7Send highlighted chat", "&7messages to your teammates!", "", "&eClick to open!");
                    yml.addDefault(TRACKER_MENU_TITLE, "&8Purchase Enemy Tracker");
                    saveItem(yml, MainConfig.TRACKER_MENU_TEAM_ITEM, "&cTrack Team {team}", "&7Purchase tracking upgrade", "&7for your compass which will", "&7track each player on a", "&7specific team until you", "&7die.", "", "&7Cost: &22 Emeralds", "", "{status}");
                    saveItem(yml, MainConfig.TRACKER_MENU_BACK_ITEM, "&aGo Back", "&7To Tracker & Communication");
                    yml.addDefault(COMMUNICATIONS_MENU_TITLE, "&8Quick Communications");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_BACK, "&aGo Back", "&7To Tracker & Communication");
                    if (yml.getString(PATH + MainConfig.COMMUNICATIONS_MENU_ITEMS) == null) {
                        saveCommunicationItem(yml, "1", "&aHello ( ?????????)/!", "&aHello ( ?????????)/!", "", "&eClick to send!");
                        saveCommunicationItem(yml, "2", "&aI'm coming back to base!", "&aI'm coming back to base!", "", "&eClick to send!");
                        saveCommunicationItem(yml, "3", "&aI'm defending!", "&aI'm defending!", "", "&eClick to send!");
                        saveCommunicationItem(yml, "4", "&aI''m attacking {team}", "&aI'm attacking!", "&7You will be able to select", "&7the Team", "", "&eClick to send!");
                        saveCommunicationItem(yml, "5", "&aI'm collecting {resource}", "&aI'm collecting resources!", "&7You will be able to select", "&7the Resource", "", "&eClick to send!");
                        saveCommunicationItem(yml, "6", "&aI have {resource}", "&aI have resources!", "&7You will be able to select", "&7the Resource", "", "&eClick to send!");
                        saveCommunicationItem(yml, "7", "&aThank You", "&aThank You!", "", "&eClick to send!");
                        saveCommunicationItem(yml, "8", "&aGet back to base", "&aGet back to base!", "", "&eClick to send!");
                        saveCommunicationItem(yml, "9", "&aPlease defend!", "&aPlease defend!", "", "&eClick to send!");
                        saveCommunicationItem(yml, "10", "&aLet's attack {team}", "&aLet's attack!", "&7You will be able to select", "&7the Team", "", "&eClick to send!");
                        saveCommunicationItem(yml, "11", "&aWe need {resource}", "&aWe need resources!", "&7You will be able to select", "&7the Resource", "", "&eClick to send!");
                        saveCommunicationItem(yml, "12", "&aPlayer incoming!", "&aPlayer incoming!!", "", "&eClick to send!");
                    }
                    yml.addDefault(COMMUNICATIONS_MENU_LORE, new String[]{"&7Click to send message: '{message}&7'", "&7to your teammates!", "", "&eClick to send!"});
                    yml.addDefault(COMMUNICATIONS_MENU_TEAMS_TITLE, "&8Select an option:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_TEAMS + ".back-item", "&aGo Back", "&7To Quick Communications");
                    yml.addDefault(COMMUNICATIONS_MENU_RESOURCES_TITLE, "&8Select an option:");
                    saveItem(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".back-item", "&aGo Back", "&7To Quick Communications");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".iron", "&f&lIRON");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".gold", "&6&lGOLD");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".diamond", "&b&lDIAMOND");
                    saveResource(yml, MainConfig.COMMUNICATIONS_MENU_RESOURCES + ".emerald", "&2&lEMERALD");
                    break;
            }
            l.getYml().options().copyDefaults(true);
            l.save();
        }
    }

    public static Language getLang(Player player) {
        return Language.getPlayerLanguage(player);
    }

    public static YamlConfiguration getYml(Player player) {
        return getLang(player).getYml();
    }

    private void saveResource(YamlConfiguration yml, String path, String resourceName) {
        path = PATH + path;
        yml.addDefault(path + ".resource-name", resourceName);
    }

    private void saveItem(YamlConfiguration yml, String path, String displayName, String... lore) {
        path = PATH + path;
        yml.addDefault(path+".display-name", displayName);
        yml.addDefault(path+".lore", lore);
    }

    private void saveCommunicationItem(YamlConfiguration yml, String path, String message, String displayName, String... lore) {
        path = PATH + MainConfig.COMMUNICATIONS_MENU_ITEMS +"."+ path;
        yml.addDefault(path+".message", message);
        yml.addDefault(path+".display-name", displayName);
        yml.addDefault(path+".lore", lore);
    }

    public static final String
            NOT_ALL_BEDS_DESTROYED = PATH + "messages.not-all-beds-destroyed",
            PURCHASED = PATH + "messages.purchase-message",
            NOT_ENOUGH_RESOURCE = PATH + "messages.not-enough-resource",
            ALREADY_TRACKING = PATH + "messages.already-tracking",
            STATUS_UNLOCKED = PATH + "tracker-status.unlocked",
            STATUS_LOCKED = PATH + "tracker-status.locked",
            STATUS_NOT_ENOUGH = PATH + "tracker-status.not-enough",
            ACTION_BAR_TRACKING = PATH + "action-bar.tracking-format",
            TEAM_MESSAGE_FORMAT = PATH + "team-message-format",
            MAIN_MENU_TITLE = PATH + "menus.main-menu.title",
            TRACKER_MENU_TITLE = PATH + "menus.tracker-menu.title",
            COMMUNICATIONS_MENU_TITLE = PATH + "menus.communications.title",
            COMMUNICATIONS_MENU_RESOURCES_TITLE = PATH + "communication-menus.resources.title",
            COMMUNICATIONS_MENU_TEAMS_TITLE = PATH + "communication-menus.teams.title",
            COMMUNICATIONS_MENU_LORE = PATH + "communication-menus.lore";

}
