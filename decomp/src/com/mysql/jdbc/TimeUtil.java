/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.TimeZone;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TimeUtil
/*      */ {
/*      */   static final Map ABBREVIATED_TIMEZONES;
/*   48 */   static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
/*      */   
/*      */   static final Map TIMEZONE_MAPPINGS;
/*      */   
/*      */   static  {
/*   53 */     tempMap = new HashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   58 */     tempMap.put("Romance", "Europe/Paris");
/*   59 */     tempMap.put("Romance Standard Time", "Europe/Paris");
/*   60 */     tempMap.put("Warsaw", "Europe/Warsaw");
/*   61 */     tempMap.put("Central Europe", "Europe/Prague");
/*   62 */     tempMap.put("Central Europe Standard Time", "Europe/Prague");
/*   63 */     tempMap.put("Prague Bratislava", "Europe/Prague");
/*   64 */     tempMap.put("W. Central Africa Standard Time", "Africa/Luanda");
/*   65 */     tempMap.put("FLE", "Europe/Helsinki");
/*   66 */     tempMap.put("FLE Standard Time", "Europe/Helsinki");
/*   67 */     tempMap.put("GFT", "Europe/Athens");
/*   68 */     tempMap.put("GFT Standard Time", "Europe/Athens");
/*   69 */     tempMap.put("GTB", "Europe/Athens");
/*   70 */     tempMap.put("GTB Standard Time", "Europe/Athens");
/*   71 */     tempMap.put("Israel", "Asia/Jerusalem");
/*   72 */     tempMap.put("Israel Standard Time", "Asia/Jerusalem");
/*   73 */     tempMap.put("Arab", "Asia/Riyadh");
/*   74 */     tempMap.put("Arab Standard Time", "Asia/Riyadh");
/*   75 */     tempMap.put("Arabic Standard Time", "Asia/Baghdad");
/*   76 */     tempMap.put("E. Africa", "Africa/Nairobi");
/*   77 */     tempMap.put("E. Africa Standard Time", "Africa/Nairobi");
/*   78 */     tempMap.put("Saudi Arabia", "Asia/Riyadh");
/*   79 */     tempMap.put("Saudi Arabia Standard Time", "Asia/Riyadh");
/*   80 */     tempMap.put("Iran", "Asia/Tehran");
/*   81 */     tempMap.put("Iran Standard Time", "Asia/Tehran");
/*   82 */     tempMap.put("Afghanistan", "Asia/Kabul");
/*   83 */     tempMap.put("Afghanistan Standard Time", "Asia/Kabul");
/*   84 */     tempMap.put("India", "Asia/Calcutta");
/*   85 */     tempMap.put("India Standard Time", "Asia/Calcutta");
/*   86 */     tempMap.put("Myanmar Standard Time", "Asia/Rangoon");
/*   87 */     tempMap.put("Nepal Standard Time", "Asia/Katmandu");
/*   88 */     tempMap.put("Sri Lanka", "Asia/Colombo");
/*   89 */     tempMap.put("Sri Lanka Standard Time", "Asia/Colombo");
/*   90 */     tempMap.put("Beijing", "Asia/Shanghai");
/*   91 */     tempMap.put("China", "Asia/Shanghai");
/*   92 */     tempMap.put("China Standard Time", "Asia/Shanghai");
/*   93 */     tempMap.put("AUS Central", "Australia/Darwin");
/*   94 */     tempMap.put("AUS Central Standard Time", "Australia/Darwin");
/*   95 */     tempMap.put("Cen. Australia", "Australia/Adelaide");
/*   96 */     tempMap.put("Cen. Australia Standard Time", "Australia/Adelaide");
/*   97 */     tempMap.put("Vladivostok", "Asia/Vladivostok");
/*   98 */     tempMap.put("Vladivostok Standard Time", "Asia/Vladivostok");
/*   99 */     tempMap.put("West Pacific", "Pacific/Guam");
/*  100 */     tempMap.put("West Pacific Standard Time", "Pacific/Guam");
/*  101 */     tempMap.put("E. South America", "America/Sao_Paulo");
/*  102 */     tempMap.put("E. South America Standard Time", "America/Sao_Paulo");
/*  103 */     tempMap.put("Greenland Standard Time", "America/Godthab");
/*  104 */     tempMap.put("Newfoundland", "America/St_Johns");
/*  105 */     tempMap.put("Newfoundland Standard Time", "America/St_Johns");
/*  106 */     tempMap.put("Pacific SA", "America/Caracas");
/*  107 */     tempMap.put("Pacific SA Standard Time", "America/Caracas");
/*  108 */     tempMap.put("SA Western", "America/Caracas");
/*  109 */     tempMap.put("SA Western Standard Time", "America/Caracas");
/*  110 */     tempMap.put("SA Pacific", "America/Bogota");
/*  111 */     tempMap.put("SA Pacific Standard Time", "America/Bogota");
/*  112 */     tempMap.put("US Eastern", "America/Indianapolis");
/*  113 */     tempMap.put("US Eastern Standard Time", "America/Indianapolis");
/*  114 */     tempMap.put("Central America Standard Time", "America/Regina");
/*  115 */     tempMap.put("Mexico", "America/Mexico_City");
/*  116 */     tempMap.put("Mexico Standard Time", "America/Mexico_City");
/*  117 */     tempMap.put("Canada Central", "America/Regina");
/*  118 */     tempMap.put("Canada Central Standard Time", "America/Regina");
/*  119 */     tempMap.put("US Mountain", "America/Phoenix");
/*  120 */     tempMap.put("US Mountain Standard Time", "America/Phoenix");
/*  121 */     tempMap.put("GMT", "GMT");
/*  122 */     tempMap.put("Ekaterinburg", "Asia/Yekaterinburg");
/*  123 */     tempMap.put("Ekaterinburg Standard Time", "Asia/Yekaterinburg");
/*  124 */     tempMap.put("West Asia", "Asia/Karachi");
/*  125 */     tempMap.put("West Asia Standard Time", "Asia/Karachi");
/*  126 */     tempMap.put("Central Asia", "Asia/Dhaka");
/*  127 */     tempMap.put("Central Asia Standard Time", "Asia/Dhaka");
/*  128 */     tempMap.put("N. Central Asia Standard Time", "Asia/Novosibirsk");
/*  129 */     tempMap.put("Bangkok", "Asia/Bangkok");
/*  130 */     tempMap.put("Bangkok Standard Time", "Asia/Bangkok");
/*  131 */     tempMap.put("North Asia Standard Time", "Asia/Krasnoyarsk");
/*  132 */     tempMap.put("SE Asia", "Asia/Bangkok");
/*  133 */     tempMap.put("SE Asia Standard Time", "Asia/Bangkok");
/*  134 */     tempMap.put("North Asia East Standard Time", "Asia/Ulaanbaatar");
/*  135 */     tempMap.put("Singapore", "Asia/Singapore");
/*  136 */     tempMap.put("Singapore Standard Time", "Asia/Singapore");
/*  137 */     tempMap.put("Taipei", "Asia/Taipei");
/*  138 */     tempMap.put("Taipei Standard Time", "Asia/Taipei");
/*  139 */     tempMap.put("W. Australia", "Australia/Perth");
/*  140 */     tempMap.put("W. Australia Standard Time", "Australia/Perth");
/*  141 */     tempMap.put("Korea", "Asia/Seoul");
/*  142 */     tempMap.put("Korea Standard Time", "Asia/Seoul");
/*  143 */     tempMap.put("Tokyo", "Asia/Tokyo");
/*  144 */     tempMap.put("Tokyo Standard Time", "Asia/Tokyo");
/*  145 */     tempMap.put("Yakutsk", "Asia/Yakutsk");
/*  146 */     tempMap.put("Yakutsk Standard Time", "Asia/Yakutsk");
/*  147 */     tempMap.put("Central European", "Europe/Belgrade");
/*  148 */     tempMap.put("Central European Standard Time", "Europe/Belgrade");
/*  149 */     tempMap.put("W. Europe", "Europe/Berlin");
/*  150 */     tempMap.put("W. Europe Standard Time", "Europe/Berlin");
/*  151 */     tempMap.put("Tasmania", "Australia/Hobart");
/*  152 */     tempMap.put("Tasmania Standard Time", "Australia/Hobart");
/*  153 */     tempMap.put("AUS Eastern", "Australia/Sydney");
/*  154 */     tempMap.put("AUS Eastern Standard Time", "Australia/Sydney");
/*  155 */     tempMap.put("E. Australia", "Australia/Brisbane");
/*  156 */     tempMap.put("E. Australia Standard Time", "Australia/Brisbane");
/*  157 */     tempMap.put("Sydney Standard Time", "Australia/Sydney");
/*  158 */     tempMap.put("Central Pacific", "Pacific/Guadalcanal");
/*  159 */     tempMap.put("Central Pacific Standard Time", "Pacific/Guadalcanal");
/*  160 */     tempMap.put("Dateline", "Pacific/Majuro");
/*  161 */     tempMap.put("Dateline Standard Time", "Pacific/Majuro");
/*  162 */     tempMap.put("Fiji", "Pacific/Fiji");
/*  163 */     tempMap.put("Fiji Standard Time", "Pacific/Fiji");
/*  164 */     tempMap.put("Samoa", "Pacific/Apia");
/*  165 */     tempMap.put("Samoa Standard Time", "Pacific/Apia");
/*  166 */     tempMap.put("Hawaiian", "Pacific/Honolulu");
/*  167 */     tempMap.put("Hawaiian Standard Time", "Pacific/Honolulu");
/*  168 */     tempMap.put("Alaskan", "America/Anchorage");
/*  169 */     tempMap.put("Alaskan Standard Time", "America/Anchorage");
/*  170 */     tempMap.put("Pacific", "America/Los_Angeles");
/*  171 */     tempMap.put("Pacific Standard Time", "America/Los_Angeles");
/*  172 */     tempMap.put("Mexico Standard Time 2", "America/Chihuahua");
/*  173 */     tempMap.put("Mountain", "America/Denver");
/*  174 */     tempMap.put("Mountain Standard Time", "America/Denver");
/*  175 */     tempMap.put("Central", "America/Chicago");
/*  176 */     tempMap.put("Central Standard Time", "America/Chicago");
/*  177 */     tempMap.put("Eastern", "America/New_York");
/*  178 */     tempMap.put("Eastern Standard Time", "America/New_York");
/*  179 */     tempMap.put("E. Europe", "Europe/Bucharest");
/*  180 */     tempMap.put("E. Europe Standard Time", "Europe/Bucharest");
/*  181 */     tempMap.put("Egypt", "Africa/Cairo");
/*  182 */     tempMap.put("Egypt Standard Time", "Africa/Cairo");
/*  183 */     tempMap.put("South Africa", "Africa/Harare");
/*  184 */     tempMap.put("South Africa Standard Time", "Africa/Harare");
/*  185 */     tempMap.put("Atlantic", "America/Halifax");
/*  186 */     tempMap.put("Atlantic Standard Time", "America/Halifax");
/*  187 */     tempMap.put("SA Eastern", "America/Buenos_Aires");
/*  188 */     tempMap.put("SA Eastern Standard Time", "America/Buenos_Aires");
/*  189 */     tempMap.put("Mid-Atlantic", "Atlantic/South_Georgia");
/*  190 */     tempMap.put("Mid-Atlantic Standard Time", "Atlantic/South_Georgia");
/*  191 */     tempMap.put("Azores", "Atlantic/Azores");
/*  192 */     tempMap.put("Azores Standard Time", "Atlantic/Azores");
/*  193 */     tempMap.put("Cape Verde Standard Time", "Atlantic/Cape_Verde");
/*  194 */     tempMap.put("Russian", "Europe/Moscow");
/*  195 */     tempMap.put("Russian Standard Time", "Europe/Moscow");
/*  196 */     tempMap.put("New Zealand", "Pacific/Auckland");
/*  197 */     tempMap.put("New Zealand Standard Time", "Pacific/Auckland");
/*  198 */     tempMap.put("Tonga Standard Time", "Pacific/Tongatapu");
/*  199 */     tempMap.put("Arabian", "Asia/Muscat");
/*  200 */     tempMap.put("Arabian Standard Time", "Asia/Muscat");
/*  201 */     tempMap.put("Caucasus", "Asia/Tbilisi");
/*  202 */     tempMap.put("Caucasus Standard Time", "Asia/Tbilisi");
/*  203 */     tempMap.put("GMT Standard Time", "GMT");
/*  204 */     tempMap.put("Greenwich", "GMT");
/*  205 */     tempMap.put("Greenwich Standard Time", "GMT");
/*  206 */     tempMap.put("UTC", "GMT");
/*      */ 
/*      */     
/*  209 */     Iterator entries = tempMap.entrySet().iterator();
/*  210 */     Map entryMap = new HashMap(tempMap.size());
/*      */     
/*  212 */     while (entries.hasNext()) {
/*  213 */       String name = ((Map.Entry)entries.next()).getValue().toString();
/*  214 */       entryMap.put(name, name);
/*      */     } 
/*      */     
/*  217 */     tempMap.putAll(entryMap);
/*      */     
/*  219 */     TIMEZONE_MAPPINGS = Collections.unmodifiableMap(tempMap);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  224 */     tempMap = new HashMap();
/*      */     
/*  226 */     tempMap.put("ACST", new String[] { "America/Porto_Acre" });
/*  227 */     tempMap.put("ACT", new String[] { "America/Porto_Acre" });
/*  228 */     tempMap.put("ADDT", new String[] { "America/Pangnirtung" });
/*  229 */     tempMap.put("ADMT", new String[] { "Africa/Asmera", "Africa/Addis_Ababa" });
/*      */     
/*  231 */     tempMap.put("ADT", new String[] { "Atlantic/Bermuda", "Asia/Baghdad", "America/Thule", "America/Goose_Bay", "America/Halifax", "America/Glace_Bay", "America/Pangnirtung", "America/Barbados", "America/Martinique" });
/*      */ 
/*      */ 
/*      */     
/*  235 */     tempMap.put("AFT", new String[] { "Asia/Kabul" });
/*  236 */     tempMap.put("AHDT", new String[] { "America/Anchorage" });
/*  237 */     tempMap.put("AHST", new String[] { "America/Anchorage" });
/*  238 */     tempMap.put("AHWT", new String[] { "America/Anchorage" });
/*  239 */     tempMap.put("AKDT", new String[] { "America/Juneau", "America/Yakutat", "America/Anchorage", "America/Nome" });
/*      */     
/*  241 */     tempMap.put("AKST", new String[] { "Asia/Aqtobe", "America/Juneau", "America/Yakutat", "America/Anchorage", "America/Nome" });
/*      */     
/*  243 */     tempMap.put("AKT", new String[] { "Asia/Aqtobe" });
/*  244 */     tempMap.put("AKTST", new String[] { "Asia/Aqtobe" });
/*  245 */     tempMap.put("AKWT", new String[] { "America/Juneau", "America/Yakutat", "America/Anchorage", "America/Nome" });
/*      */     
/*  247 */     tempMap.put("ALMST", new String[] { "Asia/Almaty" });
/*  248 */     tempMap.put("ALMT", new String[] { "Asia/Almaty" });
/*  249 */     tempMap.put("AMST", new String[] { "Asia/Yerevan", "America/Cuiaba", "America/Porto_Velho", "America/Boa_Vista", "America/Manaus" });
/*      */     
/*  251 */     tempMap.put("AMT", new String[] { "Europe/Athens", "Europe/Amsterdam", "Asia/Yerevan", "Africa/Asmera", "America/Cuiaba", "America/Porto_Velho", "America/Boa_Vista", "America/Manaus", "America/Asuncion" });
/*      */ 
/*      */ 
/*      */     
/*  255 */     tempMap.put("ANAMT", new String[] { "Asia/Anadyr" });
/*  256 */     tempMap.put("ANAST", new String[] { "Asia/Anadyr" });
/*  257 */     tempMap.put("ANAT", new String[] { "Asia/Anadyr" });
/*  258 */     tempMap.put("ANT", new String[] { "America/Aruba", "America/Curacao" });
/*  259 */     tempMap.put("AQTST", new String[] { "Asia/Aqtobe", "Asia/Aqtau" });
/*  260 */     tempMap.put("AQTT", new String[] { "Asia/Aqtobe", "Asia/Aqtau" });
/*  261 */     tempMap.put("ARST", new String[] { "Antarctica/Palmer", "America/Buenos_Aires", "America/Rosario", "America/Cordoba", "America/Jujuy", "America/Catamarca", "America/Mendoza" });
/*      */ 
/*      */     
/*  264 */     tempMap.put("ART", new String[] { "Antarctica/Palmer", "America/Buenos_Aires", "America/Rosario", "America/Cordoba", "America/Jujuy", "America/Catamarca", "America/Mendoza" });
/*      */ 
/*      */     
/*  267 */     tempMap.put("ASHST", new String[] { "Asia/Ashkhabad" });
/*  268 */     tempMap.put("ASHT", new String[] { "Asia/Ashkhabad" });
/*  269 */     tempMap.put("AST", new String[] { "Atlantic/Bermuda", "Asia/Bahrain", "Asia/Baghdad", "Asia/Kuwait", "Asia/Qatar", "Asia/Riyadh", "Asia/Aden", "America/Thule", "America/Goose_Bay", "America/Halifax", "America/Glace_Bay", "America/Pangnirtung", "America/Anguilla", "America/Antigua", "America/Barbados", "America/Dominica", "America/Santo_Domingo", "America/Grenada", "America/Guadeloupe", "America/Martinique", "America/Montserrat", "America/Puerto_Rico", "America/St_Kitts", "America/St_Lucia", "America/Miquelon", "America/St_Vincent", "America/Tortola", "America/St_Thomas", "America/Aruba", "America/Curacao", "America/Port_of_Spain" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  280 */     tempMap.put("AWT", new String[] { "America/Puerto_Rico" });
/*  281 */     tempMap.put("AZOST", new String[] { "Atlantic/Azores" });
/*  282 */     tempMap.put("AZOT", new String[] { "Atlantic/Azores" });
/*  283 */     tempMap.put("AZST", new String[] { "Asia/Baku" });
/*  284 */     tempMap.put("AZT", new String[] { "Asia/Baku" });
/*  285 */     tempMap.put("BAKST", new String[] { "Asia/Baku" });
/*  286 */     tempMap.put("BAKT", new String[] { "Asia/Baku" });
/*  287 */     tempMap.put("BDT", new String[] { "Asia/Dacca", "America/Nome", "America/Adak" });
/*      */     
/*  289 */     tempMap.put("BEAT", new String[] { "Africa/Nairobi", "Africa/Mogadishu", "Africa/Kampala" });
/*      */     
/*  291 */     tempMap.put("BEAUT", new String[] { "Africa/Nairobi", "Africa/Dar_es_Salaam", "Africa/Kampala" });
/*      */     
/*  293 */     tempMap.put("BMT", new String[] { "Europe/Brussels", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Bucharest", "Europe/Zurich", "Asia/Baghdad", "Asia/Bangkok", "Africa/Banjul", "America/Barbados", "America/Bogota" });
/*      */ 
/*      */ 
/*      */     
/*  297 */     tempMap.put("BNT", new String[] { "Asia/Brunei" });
/*  298 */     tempMap.put("BORT", new String[] { "Asia/Ujung_Pandang", "Asia/Kuching" });
/*      */     
/*  300 */     tempMap.put("BOST", new String[] { "America/La_Paz" });
/*  301 */     tempMap.put("BOT", new String[] { "America/La_Paz" });
/*  302 */     tempMap.put("BRST", new String[] { "America/Belem", "America/Fortaleza", "America/Araguaina", "America/Maceio", "America/Sao_Paulo" });
/*      */ 
/*      */     
/*  305 */     tempMap.put("BRT", new String[] { "America/Belem", "America/Fortaleza", "America/Araguaina", "America/Maceio", "America/Sao_Paulo" });
/*      */     
/*  307 */     tempMap.put("BST", new String[] { "Europe/London", "Europe/Belfast", "Europe/Dublin", "Europe/Gibraltar", "Pacific/Pago_Pago", "Pacific/Midway", "America/Nome", "America/Adak" });
/*      */ 
/*      */     
/*  310 */     tempMap.put("BTT", new String[] { "Asia/Thimbu" });
/*  311 */     tempMap.put("BURT", new String[] { "Asia/Dacca", "Asia/Rangoon", "Asia/Calcutta" });
/*      */     
/*  313 */     tempMap.put("BWT", new String[] { "America/Nome", "America/Adak" });
/*  314 */     tempMap.put("CANT", new String[] { "Atlantic/Canary" });
/*  315 */     tempMap.put("CAST", new String[] { "Africa/Gaborone", "Africa/Khartoum" });
/*      */     
/*  317 */     tempMap.put("CAT", new String[] { "Africa/Gaborone", "Africa/Bujumbura", "Africa/Lubumbashi", "Africa/Blantyre", "Africa/Maputo", "Africa/Windhoek", "Africa/Kigali", "Africa/Khartoum", "Africa/Lusaka", "Africa/Harare", "America/Anchorage" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  322 */     tempMap.put("CCT", new String[] { "Indian/Cocos" });
/*  323 */     tempMap.put("CDDT", new String[] { "America/Rankin_Inlet" });
/*  324 */     tempMap.put("CDT", new String[] { "Asia/Harbin", "Asia/Shanghai", "Asia/Chungking", "Asia/Urumqi", "Asia/Kashgar", "Asia/Taipei", "Asia/Macao", "America/Chicago", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Knox", "America/Indiana/Vevay", "America/Louisville", "America/Menominee", "America/Rainy_River", "America/Winnipeg", "America/Pangnirtung", "America/Iqaluit", "America/Rankin_Inlet", "America/Cambridge_Bay", "America/Cancun", "America/Mexico_City", "America/Chihuahua", "America/Belize", "America/Costa_Rica", "America/Havana", "America/El_Salvador", "America/Guatemala", "America/Tegucigalpa", "America/Managua" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  336 */     tempMap.put("CEST", new String[] { "Europe/Tirane", "Europe/Andorra", "Europe/Vienna", "Europe/Minsk", "Europe/Brussels", "Europe/Sofia", "Europe/Prague", "Europe/Copenhagen", "Europe/Tallinn", "Europe/Berlin", "Europe/Gibraltar", "Europe/Athens", "Europe/Budapest", "Europe/Rome", "Europe/Riga", "Europe/Vaduz", "Europe/Vilnius", "Europe/Luxembourg", "Europe/Malta", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Monaco", "Europe/Amsterdam", "Europe/Oslo", "Europe/Warsaw", "Europe/Lisbon", "Europe/Kaliningrad", "Europe/Madrid", "Europe/Stockholm", "Europe/Zurich", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol", "Europe/Belgrade", "Africa/Algiers", "Africa/Tripoli", "Africa/Tunis", "Africa/Ceuta" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  350 */     tempMap.put("CET", new String[] { "Europe/Tirane", "Europe/Andorra", "Europe/Vienna", "Europe/Minsk", "Europe/Brussels", "Europe/Sofia", "Europe/Prague", "Europe/Copenhagen", "Europe/Tallinn", "Europe/Berlin", "Europe/Gibraltar", "Europe/Athens", "Europe/Budapest", "Europe/Rome", "Europe/Riga", "Europe/Vaduz", "Europe/Vilnius", "Europe/Luxembourg", "Europe/Malta", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Monaco", "Europe/Amsterdam", "Europe/Oslo", "Europe/Warsaw", "Europe/Lisbon", "Europe/Kaliningrad", "Europe/Madrid", "Europe/Stockholm", "Europe/Zurich", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol", "Europe/Belgrade", "Africa/Algiers", "Africa/Tripoli", "Africa/Casablanca", "Africa/Tunis", "Africa/Ceuta" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  364 */     tempMap.put("CGST", new String[] { "America/Scoresbysund" });
/*  365 */     tempMap.put("CGT", new String[] { "America/Scoresbysund" });
/*  366 */     tempMap.put("CHDT", new String[] { "America/Belize" });
/*  367 */     tempMap.put("CHUT", new String[] { "Asia/Chungking" });
/*  368 */     tempMap.put("CJT", new String[] { "Asia/Tokyo" });
/*  369 */     tempMap.put("CKHST", new String[] { "Pacific/Rarotonga" });
/*  370 */     tempMap.put("CKT", new String[] { "Pacific/Rarotonga" });
/*  371 */     tempMap.put("CLST", new String[] { "Antarctica/Palmer", "America/Santiago" });
/*      */     
/*  373 */     tempMap.put("CLT", new String[] { "Antarctica/Palmer", "America/Santiago" });
/*      */     
/*  375 */     tempMap.put("CMT", new String[] { "Europe/Copenhagen", "Europe/Chisinau", "Europe/Tiraspol", "America/St_Lucia", "America/Buenos_Aires", "America/Rosario", "America/Cordoba", "America/Jujuy", "America/Catamarca", "America/Mendoza", "America/Caracas" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  380 */     tempMap.put("COST", new String[] { "America/Bogota" });
/*  381 */     tempMap.put("COT", new String[] { "America/Bogota" });
/*  382 */     tempMap.put("CST", new String[] { "Asia/Harbin", "Asia/Shanghai", "Asia/Chungking", "Asia/Urumqi", "Asia/Kashgar", "Asia/Taipei", "Asia/Macao", "Asia/Jayapura", "Australia/Darwin", "Australia/Adelaide", "Australia/Broken_Hill", "America/Chicago", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Knox", "America/Indiana/Vevay", "America/Louisville", "America/Detroit", "America/Menominee", "America/Rainy_River", "America/Winnipeg", "America/Regina", "America/Swift_Current", "America/Pangnirtung", "America/Iqaluit", "America/Rankin_Inlet", "America/Cambridge_Bay", "America/Cancun", "America/Mexico_City", "America/Chihuahua", "America/Hermosillo", "America/Mazatlan", "America/Belize", "America/Costa_Rica", "America/Havana", "America/El_Salvador", "America/Guatemala", "America/Tegucigalpa", "America/Managua" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     tempMap.put("CUT", new String[] { "Europe/Zaporozhye" });
/*  403 */     tempMap.put("CVST", new String[] { "Atlantic/Cape_Verde" });
/*  404 */     tempMap.put("CVT", new String[] { "Atlantic/Cape_Verde" });
/*  405 */     tempMap.put("CWT", new String[] { "America/Chicago", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Knox", "America/Indiana/Vevay", "America/Louisville", "America/Menominee" });
/*      */ 
/*      */ 
/*      */     
/*  409 */     tempMap.put("CXT", new String[] { "Indian/Christmas" });
/*  410 */     tempMap.put("DACT", new String[] { "Asia/Dacca" });
/*  411 */     tempMap.put("DAVT", new String[] { "Antarctica/Davis" });
/*  412 */     tempMap.put("DDUT", new String[] { "Antarctica/DumontDUrville" });
/*  413 */     tempMap.put("DFT", new String[] { "Europe/Oslo", "Europe/Paris" });
/*  414 */     tempMap.put("DMT", new String[] { "Europe/Belfast", "Europe/Dublin" });
/*  415 */     tempMap.put("DUSST", new String[] { "Asia/Dushanbe" });
/*  416 */     tempMap.put("DUST", new String[] { "Asia/Dushanbe" });
/*  417 */     tempMap.put("EASST", new String[] { "Pacific/Easter" });
/*  418 */     tempMap.put("EAST", new String[] { "Indian/Antananarivo", "Pacific/Easter" });
/*      */     
/*  420 */     tempMap.put("EAT", new String[] { "Indian/Comoro", "Indian/Antananarivo", "Indian/Mayotte", "Africa/Djibouti", "Africa/Asmera", "Africa/Addis_Ababa", "Africa/Nairobi", "Africa/Mogadishu", "Africa/Khartoum", "Africa/Dar_es_Salaam", "Africa/Kampala" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  425 */     tempMap.put("ECT", new String[] { "Pacific/Galapagos", "America/Guayaquil" });
/*      */     
/*  427 */     tempMap.put("EDDT", new String[] { "America/Iqaluit" });
/*  428 */     tempMap.put("EDT", new String[] { "America/New_York", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Vevay", "America/Louisville", "America/Detroit", "America/Montreal", "America/Thunder_Bay", "America/Nipigon", "America/Pangnirtung", "America/Iqaluit", "America/Cancun", "America/Nassau", "America/Santo_Domingo", "America/Port-au-Prince", "America/Jamaica", "America/Grand_Turk" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  436 */     tempMap.put("EEMT", new String[] { "Europe/Minsk", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Kaliningrad", "Europe/Moscow" });
/*      */     
/*  438 */     tempMap.put("EEST", new String[] { "Europe/Minsk", "Europe/Sofia", "Europe/Tallinn", "Europe/Helsinki", "Europe/Athens", "Europe/Riga", "Europe/Vilnius", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Warsaw", "Europe/Bucharest", "Europe/Kaliningrad", "Europe/Moscow", "Europe/Istanbul", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Asia/Nicosia", "Asia/Amman", "Asia/Beirut", "Asia/Gaza", "Asia/Damascus", "Africa/Cairo" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  446 */     tempMap.put("EET", new String[] { "Europe/Minsk", "Europe/Sofia", "Europe/Tallinn", "Europe/Helsinki", "Europe/Athens", "Europe/Riga", "Europe/Vilnius", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Warsaw", "Europe/Bucharest", "Europe/Kaliningrad", "Europe/Moscow", "Europe/Istanbul", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol", "Asia/Nicosia", "Asia/Amman", "Asia/Beirut", "Asia/Gaza", "Asia/Damascus", "Africa/Cairo", "Africa/Tripoli" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  455 */     tempMap.put("EGST", new String[] { "America/Scoresbysund" });
/*  456 */     tempMap.put("EGT", new String[] { "Atlantic/Jan_Mayen", "America/Scoresbysund" });
/*      */     
/*  458 */     tempMap.put("EHDT", new String[] { "America/Santo_Domingo" });
/*  459 */     tempMap.put("EST", new String[] { "Australia/Brisbane", "Australia/Lindeman", "Australia/Hobart", "Australia/Melbourne", "Australia/Sydney", "Australia/Broken_Hill", "Australia/Lord_Howe", "America/New_York", "America/Chicago", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Knox", "America/Indiana/Vevay", "America/Louisville", "America/Detroit", "America/Menominee", "America/Montreal", "America/Thunder_Bay", "America/Nipigon", "America/Pangnirtung", "America/Iqaluit", "America/Cancun", "America/Antigua", "America/Nassau", "America/Cayman", "America/Santo_Domingo", "America/Port-au-Prince", "America/Jamaica", "America/Managua", "America/Panama", "America/Grand_Turk" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  473 */     tempMap.put("EWT", new String[] { "America/New_York", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Vevay", "America/Louisville", "America/Detroit", "America/Jamaica" });
/*      */ 
/*      */ 
/*      */     
/*  477 */     tempMap.put("FFMT", new String[] { "America/Martinique" });
/*  478 */     tempMap.put("FJST", new String[] { "Pacific/Fiji" });
/*  479 */     tempMap.put("FJT", new String[] { "Pacific/Fiji" });
/*  480 */     tempMap.put("FKST", new String[] { "Atlantic/Stanley" });
/*  481 */     tempMap.put("FKT", new String[] { "Atlantic/Stanley" });
/*  482 */     tempMap.put("FMT", new String[] { "Atlantic/Madeira", "Africa/Freetown" });
/*      */     
/*  484 */     tempMap.put("FNST", new String[] { "America/Noronha" });
/*  485 */     tempMap.put("FNT", new String[] { "America/Noronha" });
/*  486 */     tempMap.put("FRUST", new String[] { "Asia/Bishkek" });
/*  487 */     tempMap.put("FRUT", new String[] { "Asia/Bishkek" });
/*  488 */     tempMap.put("GALT", new String[] { "Pacific/Galapagos" });
/*  489 */     tempMap.put("GAMT", new String[] { "Pacific/Gambier" });
/*  490 */     tempMap.put("GBGT", new String[] { "America/Guyana" });
/*  491 */     tempMap.put("GEST", new String[] { "Asia/Tbilisi" });
/*  492 */     tempMap.put("GET", new String[] { "Asia/Tbilisi" });
/*  493 */     tempMap.put("GFT", new String[] { "America/Cayenne" });
/*  494 */     tempMap.put("GHST", new String[] { "Africa/Accra" });
/*  495 */     tempMap.put("GILT", new String[] { "Pacific/Tarawa" });
/*  496 */     tempMap.put("GMT", new String[] { "Atlantic/St_Helena", "Atlantic/Reykjavik", "Europe/London", "Europe/Belfast", "Europe/Dublin", "Europe/Gibraltar", "Africa/Porto-Novo", "Africa/Ouagadougou", "Africa/Abidjan", "Africa/Malabo", "Africa/Banjul", "Africa/Accra", "Africa/Conakry", "Africa/Bissau", "Africa/Monrovia", "Africa/Bamako", "Africa/Timbuktu", "Africa/Nouakchott", "Africa/Niamey", "Africa/Sao_Tome", "Africa/Dakar", "Africa/Freetown", "Africa/Lome" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  505 */     tempMap.put("GST", new String[] { "Atlantic/South_Georgia", "Asia/Bahrain", "Asia/Muscat", "Asia/Qatar", "Asia/Dubai", "Pacific/Guam" });
/*      */ 
/*      */     
/*  508 */     tempMap.put("GYT", new String[] { "America/Guyana" });
/*  509 */     tempMap.put("HADT", new String[] { "America/Adak" });
/*  510 */     tempMap.put("HART", new String[] { "Asia/Harbin" });
/*  511 */     tempMap.put("HAST", new String[] { "America/Adak" });
/*  512 */     tempMap.put("HAWT", new String[] { "America/Adak" });
/*  513 */     tempMap.put("HDT", new String[] { "Pacific/Honolulu" });
/*  514 */     tempMap.put("HKST", new String[] { "Asia/Hong_Kong" });
/*  515 */     tempMap.put("HKT", new String[] { "Asia/Hong_Kong" });
/*  516 */     tempMap.put("HMT", new String[] { "Atlantic/Azores", "Europe/Helsinki", "Asia/Dacca", "Asia/Calcutta", "America/Havana" });
/*      */     
/*  518 */     tempMap.put("HOVST", new String[] { "Asia/Hovd" });
/*  519 */     tempMap.put("HOVT", new String[] { "Asia/Hovd" });
/*  520 */     tempMap.put("HST", new String[] { "Pacific/Johnston", "Pacific/Honolulu" });
/*      */     
/*  522 */     tempMap.put("HWT", new String[] { "Pacific/Honolulu" });
/*  523 */     tempMap.put("ICT", new String[] { "Asia/Phnom_Penh", "Asia/Vientiane", "Asia/Bangkok", "Asia/Saigon" });
/*      */     
/*  525 */     tempMap.put("IDDT", new String[] { "Asia/Jerusalem", "Asia/Gaza" });
/*  526 */     tempMap.put("IDT", new String[] { "Asia/Jerusalem", "Asia/Gaza" });
/*  527 */     tempMap.put("IHST", new String[] { "Asia/Colombo" });
/*  528 */     tempMap.put("IMT", new String[] { "Europe/Sofia", "Europe/Istanbul", "Asia/Irkutsk" });
/*      */     
/*  530 */     tempMap.put("IOT", new String[] { "Indian/Chagos" });
/*  531 */     tempMap.put("IRKMT", new String[] { "Asia/Irkutsk" });
/*  532 */     tempMap.put("IRKST", new String[] { "Asia/Irkutsk" });
/*  533 */     tempMap.put("IRKT", new String[] { "Asia/Irkutsk" });
/*  534 */     tempMap.put("IRST", new String[] { "Asia/Tehran" });
/*  535 */     tempMap.put("IRT", new String[] { "Asia/Tehran" });
/*  536 */     tempMap.put("ISST", new String[] { "Atlantic/Reykjavik" });
/*  537 */     tempMap.put("IST", new String[] { "Atlantic/Reykjavik", "Europe/Belfast", "Europe/Dublin", "Asia/Dacca", "Asia/Thimbu", "Asia/Calcutta", "Asia/Jerusalem", "Asia/Katmandu", "Asia/Karachi", "Asia/Gaza", "Asia/Colombo" });
/*      */ 
/*      */ 
/*      */     
/*  541 */     tempMap.put("JAYT", new String[] { "Asia/Jayapura" });
/*  542 */     tempMap.put("JMT", new String[] { "Atlantic/St_Helena", "Asia/Jerusalem" });
/*      */     
/*  544 */     tempMap.put("JST", new String[] { "Asia/Rangoon", "Asia/Dili", "Asia/Ujung_Pandang", "Asia/Tokyo", "Asia/Kuala_Lumpur", "Asia/Kuching", "Asia/Manila", "Asia/Singapore", "Pacific/Nauru" });
/*      */ 
/*      */ 
/*      */     
/*  548 */     tempMap.put("KART", new String[] { "Asia/Karachi" });
/*  549 */     tempMap.put("KAST", new String[] { "Asia/Kashgar" });
/*  550 */     tempMap.put("KDT", new String[] { "Asia/Seoul" });
/*  551 */     tempMap.put("KGST", new String[] { "Asia/Bishkek" });
/*  552 */     tempMap.put("KGT", new String[] { "Asia/Bishkek" });
/*  553 */     tempMap.put("KMT", new String[] { "Europe/Vilnius", "Europe/Kiev", "America/Cayman", "America/Jamaica", "America/St_Vincent", "America/Grand_Turk" });
/*      */ 
/*      */     
/*  556 */     tempMap.put("KOST", new String[] { "Pacific/Kosrae" });
/*  557 */     tempMap.put("KRAMT", new String[] { "Asia/Krasnoyarsk" });
/*  558 */     tempMap.put("KRAST", new String[] { "Asia/Krasnoyarsk" });
/*  559 */     tempMap.put("KRAT", new String[] { "Asia/Krasnoyarsk" });
/*  560 */     tempMap.put("KST", new String[] { "Asia/Seoul", "Asia/Pyongyang" });
/*  561 */     tempMap.put("KUYMT", new String[] { "Europe/Samara" });
/*  562 */     tempMap.put("KUYST", new String[] { "Europe/Samara" });
/*  563 */     tempMap.put("KUYT", new String[] { "Europe/Samara" });
/*  564 */     tempMap.put("KWAT", new String[] { "Pacific/Kwajalein" });
/*  565 */     tempMap.put("LHST", new String[] { "Australia/Lord_Howe" });
/*  566 */     tempMap.put("LINT", new String[] { "Pacific/Kiritimati" });
/*  567 */     tempMap.put("LKT", new String[] { "Asia/Colombo" });
/*  568 */     tempMap.put("LPMT", new String[] { "America/La_Paz" });
/*  569 */     tempMap.put("LRT", new String[] { "Africa/Monrovia" });
/*  570 */     tempMap.put("LST", new String[] { "Europe/Riga" });
/*  571 */     tempMap.put("M", new String[] { "Europe/Moscow" });
/*  572 */     tempMap.put("MADST", new String[] { "Atlantic/Madeira" });
/*  573 */     tempMap.put("MAGMT", new String[] { "Asia/Magadan" });
/*  574 */     tempMap.put("MAGST", new String[] { "Asia/Magadan" });
/*  575 */     tempMap.put("MAGT", new String[] { "Asia/Magadan" });
/*  576 */     tempMap.put("MALT", new String[] { "Asia/Kuala_Lumpur", "Asia/Singapore" });
/*      */     
/*  578 */     tempMap.put("MART", new String[] { "Pacific/Marquesas" });
/*  579 */     tempMap.put("MAWT", new String[] { "Antarctica/Mawson" });
/*  580 */     tempMap.put("MDDT", new String[] { "America/Cambridge_Bay", "America/Yellowknife", "America/Inuvik" });
/*      */     
/*  582 */     tempMap.put("MDST", new String[] { "Europe/Moscow" });
/*  583 */     tempMap.put("MDT", new String[] { "America/Denver", "America/Phoenix", "America/Boise", "America/Regina", "America/Swift_Current", "America/Edmonton", "America/Cambridge_Bay", "America/Yellowknife", "America/Inuvik", "America/Chihuahua", "America/Hermosillo", "America/Mazatlan" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  588 */     tempMap.put("MET", new String[] { "Europe/Tirane", "Europe/Andorra", "Europe/Vienna", "Europe/Minsk", "Europe/Brussels", "Europe/Sofia", "Europe/Prague", "Europe/Copenhagen", "Europe/Tallinn", "Europe/Berlin", "Europe/Gibraltar", "Europe/Athens", "Europe/Budapest", "Europe/Rome", "Europe/Riga", "Europe/Vaduz", "Europe/Vilnius", "Europe/Luxembourg", "Europe/Malta", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Monaco", "Europe/Amsterdam", "Europe/Oslo", "Europe/Warsaw", "Europe/Lisbon", "Europe/Kaliningrad", "Europe/Madrid", "Europe/Stockholm", "Europe/Zurich", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol", "Europe/Belgrade", "Africa/Algiers", "Africa/Tripoli", "Africa/Casablanca", "Africa/Tunis", "Africa/Ceuta" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  602 */     tempMap.put("MHT", new String[] { "Pacific/Majuro", "Pacific/Kwajalein" });
/*      */     
/*  604 */     tempMap.put("MMT", new String[] { "Indian/Maldives", "Europe/Minsk", "Europe/Moscow", "Asia/Rangoon", "Asia/Ujung_Pandang", "Asia/Colombo", "Pacific/Easter", "Africa/Monrovia", "America/Managua", "America/Montevideo" });
/*      */ 
/*      */ 
/*      */     
/*  608 */     tempMap.put("MOST", new String[] { "Asia/Macao" });
/*  609 */     tempMap.put("MOT", new String[] { "Asia/Macao" });
/*  610 */     tempMap.put("MPT", new String[] { "Pacific/Saipan" });
/*  611 */     tempMap.put("MSK", new String[] { "Europe/Minsk", "Europe/Tallinn", "Europe/Riga", "Europe/Vilnius", "Europe/Chisinau", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol" });
/*      */ 
/*      */ 
/*      */     
/*  615 */     tempMap.put("MST", new String[] { "Europe/Moscow", "America/Denver", "America/Phoenix", "America/Boise", "America/Regina", "America/Swift_Current", "America/Edmonton", "America/Dawson_Creek", "America/Cambridge_Bay", "America/Yellowknife", "America/Inuvik", "America/Mexico_City", "America/Chihuahua", "America/Hermosillo", "America/Mazatlan", "America/Tijuana" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  622 */     tempMap.put("MUT", new String[] { "Indian/Mauritius" });
/*  623 */     tempMap.put("MVT", new String[] { "Indian/Maldives" });
/*  624 */     tempMap.put("MWT", new String[] { "America/Denver", "America/Phoenix", "America/Boise" });
/*      */     
/*  626 */     tempMap.put("MYT", new String[] { "Asia/Kuala_Lumpur", "Asia/Kuching" });
/*      */ 
/*      */     
/*  629 */     tempMap.put("NCST", new String[] { "Pacific/Noumea" });
/*  630 */     tempMap.put("NCT", new String[] { "Pacific/Noumea" });
/*  631 */     tempMap.put("NDT", new String[] { "America/Nome", "America/Adak", "America/St_Johns", "America/Goose_Bay" });
/*      */     
/*  633 */     tempMap.put("NEGT", new String[] { "America/Paramaribo" });
/*  634 */     tempMap.put("NFT", new String[] { "Europe/Paris", "Europe/Oslo", "Pacific/Norfolk" });
/*      */     
/*  636 */     tempMap.put("NMT", new String[] { "Pacific/Norfolk" });
/*  637 */     tempMap.put("NOVMT", new String[] { "Asia/Novosibirsk" });
/*  638 */     tempMap.put("NOVST", new String[] { "Asia/Novosibirsk" });
/*  639 */     tempMap.put("NOVT", new String[] { "Asia/Novosibirsk" });
/*  640 */     tempMap.put("NPT", new String[] { "Asia/Katmandu" });
/*  641 */     tempMap.put("NRT", new String[] { "Pacific/Nauru" });
/*  642 */     tempMap.put("NST", new String[] { "Europe/Amsterdam", "Pacific/Pago_Pago", "Pacific/Midway", "America/Nome", "America/Adak", "America/St_Johns", "America/Goose_Bay" });
/*      */ 
/*      */     
/*  645 */     tempMap.put("NUT", new String[] { "Pacific/Niue" });
/*  646 */     tempMap.put("NWT", new String[] { "America/Nome", "America/Adak" });
/*  647 */     tempMap.put("NZDT", new String[] { "Antarctica/McMurdo" });
/*  648 */     tempMap.put("NZHDT", new String[] { "Pacific/Auckland" });
/*  649 */     tempMap.put("NZST", new String[] { "Antarctica/McMurdo", "Pacific/Auckland" });
/*      */     
/*  651 */     tempMap.put("OMSMT", new String[] { "Asia/Omsk" });
/*  652 */     tempMap.put("OMSST", new String[] { "Asia/Omsk" });
/*  653 */     tempMap.put("OMST", new String[] { "Asia/Omsk" });
/*  654 */     tempMap.put("PDDT", new String[] { "America/Inuvik", "America/Whitehorse", "America/Dawson" });
/*      */     
/*  656 */     tempMap.put("PDT", new String[] { "America/Los_Angeles", "America/Juneau", "America/Boise", "America/Vancouver", "America/Dawson_Creek", "America/Inuvik", "America/Whitehorse", "America/Dawson", "America/Tijuana" });
/*      */ 
/*      */ 
/*      */     
/*  660 */     tempMap.put("PEST", new String[] { "America/Lima" });
/*  661 */     tempMap.put("PET", new String[] { "America/Lima" });
/*  662 */     tempMap.put("PETMT", new String[] { "Asia/Kamchatka" });
/*  663 */     tempMap.put("PETST", new String[] { "Asia/Kamchatka" });
/*  664 */     tempMap.put("PETT", new String[] { "Asia/Kamchatka" });
/*  665 */     tempMap.put("PGT", new String[] { "Pacific/Port_Moresby" });
/*  666 */     tempMap.put("PHOT", new String[] { "Pacific/Enderbury" });
/*  667 */     tempMap.put("PHST", new String[] { "Asia/Manila" });
/*  668 */     tempMap.put("PHT", new String[] { "Asia/Manila" });
/*  669 */     tempMap.put("PKT", new String[] { "Asia/Karachi" });
/*  670 */     tempMap.put("PMDT", new String[] { "America/Miquelon" });
/*  671 */     tempMap.put("PMMT", new String[] { "Pacific/Port_Moresby" });
/*  672 */     tempMap.put("PMST", new String[] { "America/Miquelon" });
/*  673 */     tempMap.put("PMT", new String[] { "Antarctica/DumontDUrville", "Europe/Prague", "Europe/Paris", "Europe/Monaco", "Africa/Algiers", "Africa/Tunis", "America/Panama", "America/Paramaribo" });
/*      */ 
/*      */ 
/*      */     
/*  677 */     tempMap.put("PNT", new String[] { "Pacific/Pitcairn" });
/*  678 */     tempMap.put("PONT", new String[] { "Pacific/Ponape" });
/*  679 */     tempMap.put("PPMT", new String[] { "America/Port-au-Prince" });
/*  680 */     tempMap.put("PST", new String[] { "Pacific/Pitcairn", "America/Los_Angeles", "America/Juneau", "America/Boise", "America/Vancouver", "America/Dawson_Creek", "America/Inuvik", "America/Whitehorse", "America/Dawson", "America/Hermosillo", "America/Mazatlan", "America/Tijuana" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  685 */     tempMap.put("PWT", new String[] { "Pacific/Palau", "America/Los_Angeles", "America/Juneau", "America/Boise", "America/Tijuana" });
/*      */ 
/*      */     
/*  688 */     tempMap.put("PYST", new String[] { "America/Asuncion" });
/*  689 */     tempMap.put("PYT", new String[] { "America/Asuncion" });
/*  690 */     tempMap.put("QMT", new String[] { "America/Guayaquil" });
/*  691 */     tempMap.put("RET", new String[] { "Indian/Reunion" });
/*  692 */     tempMap.put("RMT", new String[] { "Atlantic/Reykjavik", "Europe/Rome", "Europe/Riga", "Asia/Rangoon" });
/*      */     
/*  694 */     tempMap.put("S", new String[] { "Europe/Moscow" });
/*  695 */     tempMap.put("SAMMT", new String[] { "Europe/Samara" });
/*  696 */     tempMap.put("SAMST", new String[] { "Europe/Samara", "Asia/Samarkand" });
/*      */ 
/*      */     
/*  699 */     tempMap.put("SAMT", new String[] { "Europe/Samara", "Asia/Samarkand", "Pacific/Pago_Pago", "Pacific/Apia" });
/*      */     
/*  701 */     tempMap.put("SAST", new String[] { "Africa/Maseru", "Africa/Windhoek", "Africa/Johannesburg", "Africa/Mbabane" });
/*      */     
/*  703 */     tempMap.put("SBT", new String[] { "Pacific/Guadalcanal" });
/*  704 */     tempMap.put("SCT", new String[] { "Indian/Mahe" });
/*  705 */     tempMap.put("SDMT", new String[] { "America/Santo_Domingo" });
/*  706 */     tempMap.put("SGT", new String[] { "Asia/Singapore" });
/*  707 */     tempMap.put("SHEST", new String[] { "Asia/Aqtau" });
/*  708 */     tempMap.put("SHET", new String[] { "Asia/Aqtau" });
/*  709 */     tempMap.put("SJMT", new String[] { "America/Costa_Rica" });
/*  710 */     tempMap.put("SLST", new String[] { "Africa/Freetown" });
/*  711 */     tempMap.put("SMT", new String[] { "Atlantic/Stanley", "Europe/Stockholm", "Europe/Simferopol", "Asia/Phnom_Penh", "Asia/Vientiane", "Asia/Kuala_Lumpur", "Asia/Singapore", "Asia/Saigon", "America/Santiago" });
/*      */ 
/*      */ 
/*      */     
/*  715 */     tempMap.put("SRT", new String[] { "America/Paramaribo" });
/*  716 */     tempMap.put("SST", new String[] { "Pacific/Pago_Pago", "Pacific/Midway" });
/*      */     
/*  718 */     tempMap.put("SVEMT", new String[] { "Asia/Yekaterinburg" });
/*  719 */     tempMap.put("SVEST", new String[] { "Asia/Yekaterinburg" });
/*  720 */     tempMap.put("SVET", new String[] { "Asia/Yekaterinburg" });
/*  721 */     tempMap.put("SWAT", new String[] { "Africa/Windhoek" });
/*  722 */     tempMap.put("SYOT", new String[] { "Antarctica/Syowa" });
/*  723 */     tempMap.put("TAHT", new String[] { "Pacific/Tahiti" });
/*  724 */     tempMap.put("TASST", new String[] { "Asia/Samarkand", "Asia/Tashkent" });
/*      */ 
/*      */     
/*  727 */     tempMap.put("TAST", new String[] { "Asia/Samarkand", "Asia/Tashkent" });
/*  728 */     tempMap.put("TBIST", new String[] { "Asia/Tbilisi" });
/*  729 */     tempMap.put("TBIT", new String[] { "Asia/Tbilisi" });
/*  730 */     tempMap.put("TBMT", new String[] { "Asia/Tbilisi" });
/*  731 */     tempMap.put("TFT", new String[] { "Indian/Kerguelen" });
/*  732 */     tempMap.put("TJT", new String[] { "Asia/Dushanbe" });
/*  733 */     tempMap.put("TKT", new String[] { "Pacific/Fakaofo" });
/*  734 */     tempMap.put("TMST", new String[] { "Asia/Ashkhabad" });
/*  735 */     tempMap.put("TMT", new String[] { "Europe/Tallinn", "Asia/Tehran", "Asia/Ashkhabad" });
/*      */     
/*  737 */     tempMap.put("TOST", new String[] { "Pacific/Tongatapu" });
/*  738 */     tempMap.put("TOT", new String[] { "Pacific/Tongatapu" });
/*  739 */     tempMap.put("TPT", new String[] { "Asia/Dili" });
/*  740 */     tempMap.put("TRST", new String[] { "Europe/Istanbul" });
/*  741 */     tempMap.put("TRT", new String[] { "Europe/Istanbul" });
/*  742 */     tempMap.put("TRUT", new String[] { "Pacific/Truk" });
/*  743 */     tempMap.put("TVT", new String[] { "Pacific/Funafuti" });
/*  744 */     tempMap.put("ULAST", new String[] { "Asia/Ulaanbaatar" });
/*  745 */     tempMap.put("ULAT", new String[] { "Asia/Ulaanbaatar" });
/*  746 */     tempMap.put("URUT", new String[] { "Asia/Urumqi" });
/*  747 */     tempMap.put("UYHST", new String[] { "America/Montevideo" });
/*  748 */     tempMap.put("UYT", new String[] { "America/Montevideo" });
/*  749 */     tempMap.put("UZST", new String[] { "Asia/Samarkand", "Asia/Tashkent" });
/*  750 */     tempMap.put("UZT", new String[] { "Asia/Samarkand", "Asia/Tashkent" });
/*  751 */     tempMap.put("VET", new String[] { "America/Caracas" });
/*  752 */     tempMap.put("VLAMT", new String[] { "Asia/Vladivostok" });
/*  753 */     tempMap.put("VLAST", new String[] { "Asia/Vladivostok" });
/*  754 */     tempMap.put("VLAT", new String[] { "Asia/Vladivostok" });
/*  755 */     tempMap.put("VUST", new String[] { "Pacific/Efate" });
/*  756 */     tempMap.put("VUT", new String[] { "Pacific/Efate" });
/*  757 */     tempMap.put("WAKT", new String[] { "Pacific/Wake" });
/*  758 */     tempMap.put("WARST", new String[] { "America/Jujuy", "America/Mendoza" });
/*      */     
/*  760 */     tempMap.put("WART", new String[] { "America/Jujuy", "America/Mendoza" });
/*      */ 
/*      */     
/*  763 */     tempMap.put("WAST", new String[] { "Africa/Ndjamena", "Africa/Windhoek" });
/*      */     
/*  765 */     tempMap.put("WAT", new String[] { "Africa/Luanda", "Africa/Porto-Novo", "Africa/Douala", "Africa/Bangui", "Africa/Ndjamena", "Africa/Kinshasa", "Africa/Brazzaville", "Africa/Malabo", "Africa/Libreville", "Africa/Banjul", "Africa/Conakry", "Africa/Bissau", "Africa/Bamako", "Africa/Nouakchott", "Africa/El_Aaiun", "Africa/Windhoek", "Africa/Niamey", "Africa/Lagos", "Africa/Dakar", "Africa/Freetown" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  772 */     tempMap.put("WEST", new String[] { "Atlantic/Faeroe", "Atlantic/Azores", "Atlantic/Madeira", "Atlantic/Canary", "Europe/Brussels", "Europe/Luxembourg", "Europe/Monaco", "Europe/Lisbon", "Europe/Madrid", "Africa/Algiers", "Africa/Casablanca", "Africa/Ceuta" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  777 */     tempMap.put("WET", new String[] { "Atlantic/Faeroe", "Atlantic/Azores", "Atlantic/Madeira", "Atlantic/Canary", "Europe/Andorra", "Europe/Brussels", "Europe/Luxembourg", "Europe/Monaco", "Europe/Lisbon", "Europe/Madrid", "Africa/Algiers", "Africa/Casablanca", "Africa/El_Aaiun", "Africa/Ceuta" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  782 */     tempMap.put("WFT", new String[] { "Pacific/Wallis" });
/*  783 */     tempMap.put("WGST", new String[] { "America/Godthab" });
/*  784 */     tempMap.put("WGT", new String[] { "America/Godthab" });
/*  785 */     tempMap.put("WMT", new String[] { "Europe/Vilnius", "Europe/Warsaw" });
/*  786 */     tempMap.put("WST", new String[] { "Antarctica/Casey", "Pacific/Apia", "Australia/Perth" });
/*      */     
/*  788 */     tempMap.put("YAKMT", new String[] { "Asia/Yakutsk" });
/*  789 */     tempMap.put("YAKST", new String[] { "Asia/Yakutsk" });
/*  790 */     tempMap.put("YAKT", new String[] { "Asia/Yakutsk" });
/*  791 */     tempMap.put("YAPT", new String[] { "Pacific/Yap" });
/*  792 */     tempMap.put("YDDT", new String[] { "America/Whitehorse", "America/Dawson" });
/*      */     
/*  794 */     tempMap.put("YDT", new String[] { "America/Yakutat", "America/Whitehorse", "America/Dawson" });
/*      */     
/*  796 */     tempMap.put("YEKMT", new String[] { "Asia/Yekaterinburg" });
/*  797 */     tempMap.put("YEKST", new String[] { "Asia/Yekaterinburg" });
/*  798 */     tempMap.put("YEKT", new String[] { "Asia/Yekaterinburg" });
/*  799 */     tempMap.put("YERST", new String[] { "Asia/Yerevan" });
/*  800 */     tempMap.put("YERT", new String[] { "Asia/Yerevan" });
/*  801 */     tempMap.put("YST", new String[] { "America/Yakutat", "America/Whitehorse", "America/Dawson" });
/*      */     
/*  803 */     tempMap.put("YWT", new String[] { "America/Yakutat" });
/*      */     
/*  805 */     ABBREVIATED_TIMEZONES = Collections.unmodifiableMap(tempMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Time changeTimezone(MySQLConnection conn, Calendar sessionCalendar, Calendar targetCalendar, Time t, TimeZone fromTz, TimeZone toTz, boolean rollForward) {
/*  829 */     if (conn != null) {
/*  830 */       if (conn.getUseTimezone() && !conn.getNoTimezoneConversionForTimeType()) {
/*      */ 
/*      */         
/*  833 */         Calendar fromCal = Calendar.getInstance(fromTz);
/*  834 */         fromCal.setTime(t);
/*      */         
/*  836 */         int fromOffset = fromCal.get(15) + fromCal.get(16);
/*      */         
/*  838 */         Calendar toCal = Calendar.getInstance(toTz);
/*  839 */         toCal.setTime(t);
/*      */         
/*  841 */         int toOffset = toCal.get(15) + toCal.get(16);
/*      */         
/*  843 */         int offsetDiff = fromOffset - toOffset;
/*  844 */         long toTime = toCal.getTime().getTime();
/*      */         
/*  846 */         if (rollForward || (conn.isServerTzUTC() && !conn.isClientTzUTC())) {
/*  847 */           toTime += offsetDiff;
/*      */         } else {
/*  849 */           toTime -= offsetDiff;
/*      */         } 
/*      */         
/*  852 */         return new Time(toTime);
/*      */       } 
/*      */       
/*  855 */       if (conn.getUseJDBCCompliantTimezoneShift() && 
/*  856 */         targetCalendar != null)
/*      */       {
/*  858 */         return new Time(jdbcCompliantZoneShift(sessionCalendar, targetCalendar, t));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  867 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Timestamp changeTimezone(MySQLConnection conn, Calendar sessionCalendar, Calendar targetCalendar, Timestamp tstamp, TimeZone fromTz, TimeZone toTz, boolean rollForward) {
/*  891 */     if (conn != null) {
/*  892 */       if (conn.getUseTimezone()) {
/*      */         
/*  894 */         Calendar fromCal = Calendar.getInstance(fromTz);
/*  895 */         fromCal.setTime(tstamp);
/*      */         
/*  897 */         int fromOffset = fromCal.get(15) + fromCal.get(16);
/*      */         
/*  899 */         Calendar toCal = Calendar.getInstance(toTz);
/*  900 */         toCal.setTime(tstamp);
/*      */         
/*  902 */         int toOffset = toCal.get(15) + toCal.get(16);
/*      */         
/*  904 */         int offsetDiff = fromOffset - toOffset;
/*  905 */         long toTime = toCal.getTime().getTime();
/*      */         
/*  907 */         if (rollForward || (conn.isServerTzUTC() && !conn.isClientTzUTC())) {
/*  908 */           toTime += offsetDiff;
/*      */         } else {
/*  910 */           toTime -= offsetDiff;
/*      */         } 
/*      */         
/*  913 */         return new Timestamp(toTime);
/*      */       } 
/*      */       
/*  916 */       if (conn.getUseJDBCCompliantTimezoneShift() && 
/*  917 */         targetCalendar != null) {
/*      */         
/*  919 */         Timestamp adjustedTimestamp = new Timestamp(jdbcCompliantZoneShift(sessionCalendar, targetCalendar, tstamp));
/*      */ 
/*      */ 
/*      */         
/*  923 */         adjustedTimestamp.setNanos(tstamp.getNanos());
/*      */         
/*  925 */         return adjustedTimestamp;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  930 */     return tstamp;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static long jdbcCompliantZoneShift(Calendar sessionCalendar, Calendar targetCalendar, Date dt) {
/*  936 */     if (sessionCalendar == null) {
/*  937 */       sessionCalendar = new GregorianCalendar();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  944 */     Date origCalDate = targetCalendar.getTime();
/*  945 */     Date origSessionDate = sessionCalendar.getTime();
/*      */     
/*      */     try {
/*  948 */       sessionCalendar.setTime(dt);
/*      */       
/*  950 */       targetCalendar.set(1, sessionCalendar.get(1));
/*  951 */       targetCalendar.set(2, sessionCalendar.get(2));
/*  952 */       targetCalendar.set(5, sessionCalendar.get(5));
/*      */       
/*  954 */       targetCalendar.set(11, sessionCalendar.get(11));
/*  955 */       targetCalendar.set(12, sessionCalendar.get(12));
/*  956 */       targetCalendar.set(13, sessionCalendar.get(13));
/*  957 */       targetCalendar.set(14, sessionCalendar.get(14));
/*      */       
/*  959 */       return targetCalendar.getTime().getTime();
/*      */     } finally {
/*      */       
/*  962 */       sessionCalendar.setTime(origSessionDate);
/*  963 */       targetCalendar.setTime(origCalDate);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final Date fastDateCreate(boolean useGmtConversion, Calendar gmtCalIfNeeded, Calendar cal, int year, int month, int day) {
/*  975 */     Calendar dateCal = cal;
/*      */     
/*  977 */     if (useGmtConversion) {
/*      */       
/*  979 */       if (gmtCalIfNeeded == null) {
/*  980 */         gmtCalIfNeeded = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
/*      */       }
/*  982 */       gmtCalIfNeeded.clear();
/*      */       
/*  984 */       dateCal = gmtCalIfNeeded;
/*      */     } 
/*      */     
/*  987 */     dateCal.clear();
/*  988 */     dateCal.set(14, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  993 */     dateCal.set(year, month - 1, day, 0, 0, 0);
/*      */     
/*  995 */     long dateAsMillis = 0L;
/*      */     
/*      */     try {
/*  998 */       dateAsMillis = dateCal.getTimeInMillis();
/*  999 */     } catch (IllegalAccessError iae) {
/*      */       
/* 1001 */       dateAsMillis = dateCal.getTime().getTime();
/*      */     } 
/*      */     
/* 1004 */     return new Date(dateAsMillis);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final Date fastDateCreate(int year, int month, int day, Calendar targetCalendar) {
/* 1010 */     Calendar dateCal = (targetCalendar == null) ? new GregorianCalendar() : targetCalendar;
/*      */     
/* 1012 */     dateCal.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1018 */     dateCal.set(year, month - 1, day, 0, 0, 0);
/* 1019 */     dateCal.set(14, 0);
/*      */     
/* 1021 */     long dateAsMillis = 0L;
/*      */     
/*      */     try {
/* 1024 */       dateAsMillis = dateCal.getTimeInMillis();
/* 1025 */     } catch (IllegalAccessError iae) {
/*      */       
/* 1027 */       dateAsMillis = dateCal.getTime().getTime();
/*      */     } 
/*      */     
/* 1030 */     return new Date(dateAsMillis);
/*      */   }
/*      */ 
/*      */   
/*      */   static final Time fastTimeCreate(Calendar cal, int hour, int minute, int second, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/* 1035 */     if (hour < 0 || hour > 24) {
/* 1036 */       throw SQLError.createSQLException("Illegal hour value '" + hour + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1041 */     if (minute < 0 || minute > 59) {
/* 1042 */       throw SQLError.createSQLException("Illegal minute value '" + minute + "'" + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1047 */     if (second < 0 || second > 59) {
/* 1048 */       throw SQLError.createSQLException("Illegal minute value '" + second + "'" + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1053 */     cal.clear();
/*      */ 
/*      */     
/* 1056 */     cal.set(1970, 0, 1, hour, minute, second);
/*      */     
/* 1058 */     long timeAsMillis = 0L;
/*      */     
/*      */     try {
/* 1061 */       timeAsMillis = cal.getTimeInMillis();
/* 1062 */     } catch (IllegalAccessError iae) {
/*      */       
/* 1064 */       timeAsMillis = cal.getTime().getTime();
/*      */     } 
/*      */     
/* 1067 */     return new Time(timeAsMillis);
/*      */   }
/*      */ 
/*      */   
/*      */   static final Time fastTimeCreate(int hour, int minute, int second, Calendar targetCalendar, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/* 1072 */     if (hour < 0 || hour > 23) {
/* 1073 */       throw SQLError.createSQLException("Illegal hour value '" + hour + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1078 */     if (minute < 0 || minute > 59) {
/* 1079 */       throw SQLError.createSQLException("Illegal minute value '" + minute + "'" + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1084 */     if (second < 0 || second > 59) {
/* 1085 */       throw SQLError.createSQLException("Illegal minute value '" + second + "'" + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1090 */     Calendar cal = (targetCalendar == null) ? new GregorianCalendar() : targetCalendar;
/* 1091 */     cal.clear();
/*      */ 
/*      */     
/* 1094 */     cal.set(1970, 0, 1, hour, minute, second);
/*      */     
/* 1096 */     long timeAsMillis = 0L;
/*      */     
/*      */     try {
/* 1099 */       timeAsMillis = cal.getTimeInMillis();
/* 1100 */     } catch (IllegalAccessError iae) {
/*      */       
/* 1102 */       timeAsMillis = cal.getTime().getTime();
/*      */     } 
/*      */     
/* 1105 */     return new Time(timeAsMillis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final Timestamp fastTimestampCreate(boolean useGmtConversion, Calendar gmtCalIfNeeded, Calendar cal, int year, int month, int day, int hour, int minute, int seconds, int secondsPart) {
/* 1113 */     cal.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1118 */     cal.set(year, month - 1, day, hour, minute, seconds);
/*      */     
/* 1120 */     int offsetDiff = 0;
/*      */     
/* 1122 */     if (useGmtConversion) {
/* 1123 */       int fromOffset = cal.get(15) + cal.get(16);
/*      */ 
/*      */       
/* 1126 */       if (gmtCalIfNeeded == null) {
/* 1127 */         gmtCalIfNeeded = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
/*      */       }
/* 1129 */       gmtCalIfNeeded.clear();
/*      */       
/* 1131 */       gmtCalIfNeeded.setTimeInMillis(cal.getTimeInMillis());
/*      */       
/* 1133 */       int toOffset = gmtCalIfNeeded.get(15) + gmtCalIfNeeded.get(16);
/*      */       
/* 1135 */       offsetDiff = fromOffset - toOffset;
/*      */     } 
/*      */     
/* 1138 */     if (secondsPart != 0) {
/* 1139 */       cal.set(14, secondsPart / 1000000);
/*      */     }
/*      */     
/* 1142 */     long tsAsMillis = 0L;
/*      */ 
/*      */     
/*      */     try {
/* 1146 */       tsAsMillis = cal.getTimeInMillis();
/* 1147 */     } catch (IllegalAccessError iae) {
/*      */       
/* 1149 */       tsAsMillis = cal.getTime().getTime();
/*      */     } 
/*      */     
/* 1152 */     Timestamp ts = new Timestamp(tsAsMillis + offsetDiff);
/*      */     
/* 1154 */     ts.setNanos(secondsPart);
/*      */     
/* 1156 */     return ts;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final Timestamp fastTimestampCreate(TimeZone tz, int year, int month, int day, int hour, int minute, int seconds, int secondsPart) {
/* 1162 */     Calendar cal = (tz == null) ? new GregorianCalendar() : new GregorianCalendar(tz);
/* 1163 */     cal.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1168 */     cal.set(year, month - 1, day, hour, minute, seconds);
/*      */     
/* 1170 */     long tsAsMillis = 0L;
/*      */     
/*      */     try {
/* 1173 */       tsAsMillis = cal.getTimeInMillis();
/* 1174 */     } catch (IllegalAccessError iae) {
/*      */       
/* 1176 */       tsAsMillis = cal.getTime().getTime();
/*      */     } 
/*      */     
/* 1179 */     Timestamp ts = new Timestamp(tsAsMillis);
/* 1180 */     ts.setNanos(secondsPart);
/*      */     
/* 1182 */     return ts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCanoncialTimezone(String timezoneStr, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/* 1198 */     if (timezoneStr == null) {
/* 1199 */       return null;
/*      */     }
/*      */     
/* 1202 */     timezoneStr = timezoneStr.trim();
/*      */ 
/*      */ 
/*      */     
/* 1206 */     if (timezoneStr.length() > 2 && (
/* 1207 */       timezoneStr.charAt(0) == '+' || timezoneStr.charAt(0) == '-') && Character.isDigit(timezoneStr.charAt(1)))
/*      */     {
/* 1209 */       return "GMT" + timezoneStr;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1214 */     int daylightIndex = StringUtils.indexOfIgnoreCase(timezoneStr, "DAYLIGHT");
/*      */ 
/*      */     
/* 1217 */     if (daylightIndex != -1) {
/* 1218 */       StringBuffer timezoneBuf = new StringBuffer();
/* 1219 */       timezoneBuf.append(timezoneStr.substring(0, daylightIndex));
/* 1220 */       timezoneBuf.append("Standard");
/* 1221 */       timezoneBuf.append(timezoneStr.substring(daylightIndex + "DAYLIGHT".length(), timezoneStr.length()));
/*      */       
/* 1223 */       timezoneStr = timezoneBuf.toString();
/*      */     } 
/*      */     
/* 1226 */     String canonicalTz = (String)TIMEZONE_MAPPINGS.get(timezoneStr);
/*      */ 
/*      */     
/* 1229 */     if (canonicalTz == null) {
/* 1230 */       String[] abbreviatedTimezone = (String[])ABBREVIATED_TIMEZONES.get(timezoneStr);
/*      */ 
/*      */       
/* 1233 */       if (abbreviatedTimezone != null)
/*      */       {
/* 1235 */         if (abbreviatedTimezone.length == 1) {
/* 1236 */           canonicalTz = abbreviatedTimezone[0];
/*      */         } else {
/* 1238 */           StringBuffer possibleTimezones = new StringBuffer('');
/*      */           
/* 1240 */           possibleTimezones.append(abbreviatedTimezone[0]);
/*      */           
/* 1242 */           for (int i = 1; i < abbreviatedTimezone.length; i++) {
/* 1243 */             possibleTimezones.append(", ");
/* 1244 */             possibleTimezones.append(abbreviatedTimezone[i]);
/*      */           } 
/*      */           
/* 1247 */           throw SQLError.createSQLException(Messages.getString("TimeUtil.TooGenericTimezoneId", new Object[] { timezoneStr, possibleTimezones }), "01S00", exceptionInterceptor);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1253 */     return canonicalTz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String timeFormattedString(int hours, int minutes, int seconds) {
/* 1262 */     StringBuffer buf = new StringBuffer(8);
/* 1263 */     if (hours < 10) {
/* 1264 */       buf.append("0");
/*      */     }
/*      */     
/* 1267 */     buf.append(hours);
/* 1268 */     buf.append(":");
/*      */     
/* 1270 */     if (minutes < 10) {
/* 1271 */       buf.append("0");
/*      */     }
/*      */     
/* 1274 */     buf.append(minutes);
/* 1275 */     buf.append(":");
/*      */     
/* 1277 */     if (seconds < 10) {
/* 1278 */       buf.append("0");
/*      */     }
/*      */     
/* 1281 */     buf.append(seconds);
/*      */     
/* 1283 */     return buf.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\TimeUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */