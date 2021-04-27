package com.example.libact

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class App: Application() {
    val Context.myApp: App
        get() = applicationContext as App

    override fun onCreate() {
        super.onCreate()
        Log.i("App", "OnCreate")
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "database").allowMainThreadQueries().fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.i("App", "onCreate(db)")
                    GlobalScope.async(Dispatchers.IO) {
                        getDB().kanjiDao().insertItem(Kanji("一", "イチ, イツ", "ひと-つ", "один, ひと(つ) тж. один год (о возрасте)"))
                        getDB().kanjiDao().insertItem(Kanji("七", "シチ", "なな, なな(つ), なの", "семь"))
                        getDB().kanjiDao().insertItem(Kanji("万", "マン ,バン", "よろず", "десять тысяч"))
                        getDB().kanjiDao().insertItem(Kanji("三", "サン", "み, み(つ), み(っつ)", "три"))
                        getDB().kanjiDao().insertItem(Kanji("上", "ジョウ ", "うえ, あ(がる)", "うえ - верх, старший\nあ(がる) - подниматься, повышаться"))
                        getDB().kanjiDao().insertItem(Kanji("下", "カ, ゲ", "した ", "низ, падать, опускаться"))
                        getDB().kanjiDao().insertItem(Kanji("中", "チュウ", "なか, うち", "середина, внутренняя часть, внутри"))
                        getDB().kanjiDao().insertItem(Kanji("九", "キュウ, ク", "ここの(つ)", "девять"))
                        getDB().kanjiDao().insertItem(Kanji("二", "ニ ,ジ", "ふた, ふた(つ)", "два"))
                        getDB().kanjiDao().insertItem(Kanji("五", "ゴ", "いつ, いつ(つ)", "пять"))
                        getDB().kanjiDao().insertItem(Kanji("人", "ジン, ニン", "ひと", "человек"))
                        getDB().kanjiDao().insertItem(Kanji("今", "コン, キン", "いま", "сейчас"))
                        getDB().kanjiDao().insertItem(Kanji("休", "キュウ", "やす(む) ", "отдыхать, пропускать (занятия)"))
                        getDB().kanjiDao().insertItem(Kanji("会", "カイ ", "あ(う)", "встречаться, встреча, собрание"))
                        getDB().kanjiDao().insertItem(Kanji("住", "ジュウ, ヂュウ, チュウ", "す(む)", "жить, проживать где-л"))
                        getDB().kanjiDao().insertItem(Kanji("何",	"カ",	"なに, なん","что?"))
                        getDB().kanjiDao().insertItem(Kanji("先",	"セン",	"さき, まず", "さき - кончик, острие, верхушка, впереди, раньше, прежде\nまず - прежде всего	острие, впереди, раньше, прежде всего"))
                        getDB().kanjiDao().insertItem(Kanji("入","ニュウ","い(る) - входить, вступать, садиться (о солнце) い(れる) - вставлять, помещать, はい(る) - входить","входить, помещать"))
                        getDB().kanjiDao().insertItem(Kanji("八","ハチ","や-, や(つ), や(っつ)","восемь"))
                        getDB().kanjiDao().insertItem(Kanji("六","ロク","む-, む(つ), む(っつ) ","шесть"))
                        getDB().kanjiDao().insertItem(Kanji("円","エン - иена","まる(い) - круглый まる - круг","круг,  иена"))
                        getDB().kanjiDao().insertItem(Kanji("出","シュツ","で(る) - выходить, появляться, выступать в соревнованиях だ(す) - вынимать,вытаскивать, выдавать ","выходить, доставать"))
                        getDB().kanjiDao().insertItem(Kanji("分","ブン - часть, フン - минута, ブ - часть","わける - делить, わけ - деление, распределение, ничья, わかれる - разделяться, расставаться, わかる - понимать, わかつ - делить на части, отличать","часть, минута, делить, разделять, понимать"))
                        getDB().kanjiDao().insertItem(Kanji("前","ゼン","まえ","перед, впереди, передний"))
                        getDB().kanjiDao().insertItem(Kanji("北","ホク","きた","север"))
                        getDB().kanjiDao().insertItem(Kanji("十","ジュウ, ジッ, ジュッ","とお","десять"))
                        getDB().kanjiDao().insertItem(Kanji("千","セン","ち","тысяча"))
                        getDB().kanjiDao().insertItem(Kanji("午","ゴ","うま - Лошадь (знак зодиака), полдень","полдень, Лошадь"))
                        getDB().kanjiDao().insertItem(Kanji("半","ハン","なかば","половина, наполовину, в середине"))
                        getDB().kanjiDao().insertItem(Kanji("南","ナン  ","みなみ","юг"))
                        getDB().kanjiDao().insertItem(Kanji("友","ユウ","とも","друг"))
                        getDB().kanjiDao().insertItem(Kanji("口","コウ, ク","くち","рот"))
                        getDB().kanjiDao().insertItem(Kanji("古","コ","ふる(い)","старый"))
                        getDB().kanjiDao().insertItem(Kanji("右","ウ, ユウ","みぎ","правый"))
                        getDB().kanjiDao().insertItem(Kanji("名","メイ ,ミョウ","な","имя, название, репутация, известность"))
                        getDB().kanjiDao().insertItem(Kanji("四","シ","よ-, よ(つ), よん","четыре"))
                        getDB().kanjiDao().insertItem(Kanji("国","コク","くに","страна, государство, родина"))
                        getDB().kanjiDao().insertItem(Kanji("土","ド, ト","つち","земля, почва, Турция"))
                        getDB().kanjiDao().insertItem(Kanji("夕","セキ","ゆう","вечер"))
                        getDB().kanjiDao().insertItem(Kanji("外","ガイ, ゲ","そと - снаружи, на улице, ほか - другой, кроме","снаружи, другой, иностранный"))
                        getDB().kanjiDao().insertItem(Kanji("多","タ","おお(い)","много, часто"))
                        getDB().kanjiDao().insertItem(Kanji("夜","ヤ","よ, よる","ночь"))
                        getDB().kanjiDao().insertItem(Kanji("大","ダイ, タイ","おお(きい)","большой"))
                        getDB().kanjiDao().insertItem(Kanji("天","テン","あま, あめ","небо"))
                        getDB().kanjiDao().insertItem(Kanji("女","ジョ, ニョ, ニョウ","おんな","женщина"))
                        getDB().kanjiDao().insertItem(Kanji("子","シ ,ス","こ ","ребенок"))
                        getDB().kanjiDao().insertItem(Kanji("学","ガク","まな(ぶ) - изучать","изучать, наука"))
                        getDB().kanjiDao().insertItem(Kanji("安","アン","やす(い )","спокойный (в соч.), дешевый"))
                        getDB().kanjiDao().insertItem(Kanji("家","カ, ケ","いえ, うち","дом"))
                        getDB().kanjiDao().insertItem(Kanji("小","ショウ","ちい(さい), こ- ","маленький"))
                        getDB().kanjiDao().insertItem(Kanji("少","ショウ","すく(ない), すこ(し)","мало, немножко"))
                        getDB().kanjiDao().insertItem(Kanji("山","サン, セン","やま","гора"))
                        getDB().kanjiDao().insertItem(Kanji("川","セン","かわ","река"))
                        getDB().kanjiDao().insertItem(Kanji("左","サ, シャ","ひだり","левый"))
                        getDB().kanjiDao().insertItem(Kanji("年","ネン","とし","год"))
                        getDB().kanjiDao().insertItem(Kanji("広","コウ","ひろ(い), ひろ(まる), ひろ(める), ひろ(がる), ひろ(げる)","широкий, просторный, расширять(ся)"))
                        getDB().kanjiDao().insertItem(Kanji("店","テン","みせ  ","магазин, кафе"))
                        getDB().kanjiDao().insertItem(Kanji("後","ゴ ,コウ","のち, うし(ろ), あと","のち - потом,\n うし(ろ) - позади, сзади\n あと - после "))
                        getDB().kanjiDao().insertItem(Kanji("思","シ","おも(う) ","думать"))
                        getDB().kanjiDao().insertItem(Kanji("手","シュ, ズ","て  ","рука"))
                        getDB().kanjiDao().insertItem(Kanji("新","シン","あたら(しい), あらた, あら-, にい-","новый, свежий"))
                        getDB().kanjiDao().insertItem(Kanji("日","ニチ, ジツ","ひ","день, солнце, Япония"))
                        getDB().kanjiDao().insertItem(Kanji("昼","チュウ","ひる","полдень"))
                        getDB().kanjiDao().insertItem(Kanji("時","ジ","とき","час, время"))
                        getDB().kanjiDao().insertItem(Kanji("書","ショ","か(く)","писать"))
                        getDB().kanjiDao().insertItem(Kanji("月","ゲツ, ガツ","つき","луна, месяц"))
                        getDB().kanjiDao().insertItem(Kanji("有","ユウ, ウ","あ(る)","существовать, быть"))
                        getDB().kanjiDao().insertItem(Kanji("朝","チョウ","あさ","утро,  в соч. тж. Корея "))
                        getDB().kanjiDao().insertItem(Kanji("木","ボク ,モク","き  ","дерево"))
                        getDB().kanjiDao().insertItem(Kanji("本","ホン","もと","ホン - книга\nもと - основа, источник"))
                        getDB().kanjiDao().insertItem(Kanji("来","ライ, タイ","く(る)"," く(る) - приходить, наступать, прибывать"))
                        getDB().kanjiDao().insertItem(Kanji("東","トウ","ひがし","восток"))
                        getDB().kanjiDao().insertItem(Kanji("校","コウ ,キョウ","","школа"))
                        getDB().kanjiDao().insertItem(Kanji("歩","ホ, ブ, フ","ある(く)","ходить пешком"))
                        getDB().kanjiDao().insertItem(Kanji( "母","ボ","はは","мать"))
                        getDB().kanjiDao().insertItem(Kanji( "毎","マイ","ごと","каждый (день и т п)"))
                        getDB().kanjiDao().insertItem(Kanji("気","キ, ケ","き","воздух, пар, атмосфера, чувства, настроение"))
                        getDB().kanjiDao().insertItem(Kanji("水","スイ","みず ","вода"))
                        getDB().kanjiDao().insertItem(Kanji("火","カ","ひ  ","огонь"))
                        getDB().kanjiDao().insertItem(Kanji("父","フ","ちち","отец"))
                        getDB().kanjiDao().insertItem(Kanji("生","セイ. ショウ","い(きる), い(かす), い(ける), う(まれる),  う(む), なま","い(きる) - жить, быть живым\nい(かす) - оживлять, оставлять в живых\nい(ける)- ставить цветы в вазу\nう(まれる) - рождаться\nう(む) - рождать, приносить\nなま - сырой, необработанный"))
                        getDB().kanjiDao().insertItem(Kanji("男","ダン, ナン","おとこ, お","мужчина"))
                        getDB().kanjiDao().insertItem(Kanji("白","ハク, ビャク","しろ, しら-, しろ(い)","しろ - белый цвет\nしら-, しろ(い) - белый"))
                        getDB().kanjiDao().insertItem(Kanji("百","ヒャク, ビャク","もも","сто"))
                        getDB().kanjiDao().insertItem(Kanji("目","モク, ボク","め  ","глаз(а), суффикс порядковых числительных"))
                        getDB().kanjiDao().insertItem(Kanji("知","チ","し(る)","знать, извещать"))
                        getDB().kanjiDao().insertItem(Kanji("社","シャ","やしろ","фирма, синтоистское святилище"))
                        getDB().kanjiDao().insertItem(Kanji("私","シ","わたくし, わたし","я, частный"))
                        getDB().kanjiDao().insertItem(Kanji("空","クウ","そら, あ(く)","пустой, небо"))
                        getDB().kanjiDao().insertItem(Kanji("立","リツ ","た(つ),  た(てる)","стоять, ставить"))
                        getDB().kanjiDao().insertItem(Kanji("紙","シ","かみ","бумага"))
                        getDB().kanjiDao().insertItem(Kanji("耳","ジ","みみ","ухо"))
                        getDB().kanjiDao().insertItem(Kanji("聞","ブン, モン","き(く) - слышать, спрашивать, き(こえる) - быть слышным ,звучать, раздаваться","слышать, спрашивать"))
                        getDB().kanjiDao().insertItem(Kanji("花","カ, ケ","はな","цветок"))
                        getDB().kanjiDao().insertItem(Kanji("行","コウ, ギョウ - строка (вертикальная)","い(く) - идти, ходить, уходить, уезжать, ゆ(く) - тж, おこな(う) -  совершать, осуществлять","идти, ходить, уходить, уезжать"))
                        getDB().kanjiDao().insertItem(Kanji("西","セイ, サイ, ス","にし","Запад"))
                        getDB().kanjiDao().insertItem(Kanji("見","ケン","み(る) - смотреть, видеть, считать, полагать, み(える) - виднеться, показываться, выглядеть, み(せる) - показывать","видеть, виднеться"))
                        getDB().kanjiDao().insertItem(Kanji("言","ゲン, ゴン","い(う)","говорить, слово, язык"))
                        getDB().kanjiDao().insertItem(Kanji("話","ワ","はな(す) - говорить, рассказывать, はなし - разговор, беседа","разговаривать, рассказывать, говорить"))
                        getDB().kanjiDao().insertItem(Kanji("語","ゴ","かた(る) - говорить, рассказывать","слово, говорить"))
                        getDB().kanjiDao().insertItem(Kanji("読","ドク ,トク, トウ","よ(む)","читать"))
                        getDB().kanjiDao().insertItem(Kanji("買","バイ","か(う)","покупать"))
                        getDB().kanjiDao().insertItem(Kanji("赤","セキ, シャク","あか ","красный"))
                        getDB().kanjiDao().insertItem(Kanji("走","ソウ","はし(る)","бежать, ехать (о машине)"))
                        getDB().kanjiDao().insertItem(Kanji("足","ソク","あし","нога"))
                        getDB().kanjiDao().insertItem(Kanji("車","シャ","くるま","машина, повозка"))
                        getDB().kanjiDao().insertItem(Kanji("近","キン  ","ちか(い)","близкий"))
                        getDB().kanjiDao().insertItem(Kanji("週","シュウ","","неделя"))
                        getDB().kanjiDao().insertItem(Kanji("道","ドウ  ","みち","дорога, путь"))
                        getDB().kanjiDao().insertItem(Kanji("金","キン, コン, ゴン","かね ","золото, металл, деньги"))
                        getDB().kanjiDao().insertItem(Kanji("銀","ギン","しろがね","серебро"))
                        getDB().kanjiDao().insertItem(Kanji("長","チョウ","なが(い)","チョウ - в соч. старший, начальник\nなが(い) - длинный, долгий"))
                        getDB().kanjiDao().insertItem(Kanji("間","カン, ケン","あいだ, ま","あいだ - промежуток, расстояние, период, между\nま - тж. комната"))
                        getDB().kanjiDao().insertItem(Kanji("雨","ウ","あめ, あま","дождь"))
                        getDB().kanjiDao().insertItem(Kanji("電","デン","","электричество"))
                        getDB().kanjiDao().insertItem(Kanji("食","ショク ","く(う) , た(べる) ","く(う) , た(べる) - есть "))
                        getDB().kanjiDao().insertItem(Kanji("飲","イン ","の(む)","пить"))
                        getDB().kanjiDao().insertItem(Kanji("駅","エキ","","станция"))
                        getDB().kanjiDao().insertItem(Kanji("高","コウ","たか - (い) - (まる) - (める)","たかい - высокий, дорогой, громкий\nたか - количество, сумма, повышение в цене\nたかまる - повышаться\nたかめる - повышать"))
                        getDB().kanjiDao().insertItem(Kanji("魚","ギョ","うお, さかな","рыба"))

                        getDB().kanjiKeyDao().insertItem(KanjiKey("一"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("丨"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("丶"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("丿"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("乀"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺄"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("乙"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("乚"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("乛"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("亅"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("二"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("亠"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("人"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("亻"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("儿"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("入"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("八"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("丷"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("冂"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("冖"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("冫"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("几"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("凵"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("刀"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺈"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("刂"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("力"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("勹"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("匕"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("匚"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("匸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("十"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("卜"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("卩"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("㔾"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("厂"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("厶"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("又"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("口"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("囗"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("土"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("士"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("夂"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("夊"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("夕"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("大"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("女"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("子"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("宀"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("寸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("小"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺍"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺌"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("尢"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("尸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("屮"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("山"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("巛"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("川"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("工"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("己"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("巾"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("干"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("幺"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("么"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("广"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("廴"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("廾"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("弋"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("弓"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("彐"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("彑"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("彡"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("彳"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("心"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("忄"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺗"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("戈"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("戶"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("户"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("戸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("手"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("扌"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("龵"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("支"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("攴"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("攵"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("文"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("斗"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("斤"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("方"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("无"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("旡"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("日"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("曰"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("月"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("木"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("欠"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("止"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("歹"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("歺"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("殳"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("毋"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("母"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("比"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("毛"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("氏"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("气"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("水"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("氵"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("氺"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("火"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("灬"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("爪"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("爫"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("父"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("爻"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("爿"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("丬"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("片"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("牙"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("牛"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("牜"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺧"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("犬"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("犭"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("玄"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("玉"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("王"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("玊"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("瓜"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("瓦"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("甘"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("生"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("用"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("田"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("疋"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺪"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("疒"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("癶"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("白"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("皮"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("皿"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("目"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺫"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("矛"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("矢"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("石"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("示"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("礻"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("禸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⽱"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("禾"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("穴"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("立"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("竹"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺮"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("米"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("糸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("糹"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("纟"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("缶"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("网"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("罓"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺳"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺫"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("羊"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺶"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺷"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("羽"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("老"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("耂"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("而"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("耒"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("耳"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("聿"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺺"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺻"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("肉"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺼"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("臣"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("自"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("至"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("臼"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("舌"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("舛"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("舟"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("艮"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("色"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("艸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⺿"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("艹"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("虍"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("虫"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("血"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("行"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("衣"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⻂"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("襾"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("西"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("覀"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("見"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("见"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("角"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⻇"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("言"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("讠"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("谷"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("豆"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("豕"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("豸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("貝"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("贝"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("赤"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("走"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("足"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⻊"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("身"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⾞"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("车"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("辛"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("辰"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("辵"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("辶"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⻍"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⻎"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("邑"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⻏"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("酉"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("釆"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("里"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("金"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("钅"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("釒"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("長"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("镸"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("长"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("門"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("门"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("阜"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⻏"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("隶"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("隹"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("雨"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("⻗"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("靑"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("青"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("非"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("面"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("靣"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("革"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("韋"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("韦"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("韭"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("音"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("頁"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("页"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("風"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("风"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("飛"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("飞"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("食"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("飠"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("饣"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("首"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("饣"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("香"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("馬"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("马"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("骨"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("高"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("髙"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("髟"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鬥"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("斗"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鬯"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鬲"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鬼"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("魚"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鱼"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鳥"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鸟"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鹵"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("卤"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鹿"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("麥"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("麦"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("麻"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("黄"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("黍"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("黑"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("黹"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("黽"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("黾"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鼎"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鼓"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鼠"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("鼻"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("齐"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("齊"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("斉"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("齒"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("齿"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("龍"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("龙"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("龜"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("龟"))
                        getDB().kanjiKeyDao().insertItem(KanjiKey("龠"))
                    }
                }
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                }
            })
            .build()
    }
    companion object {
        private lateinit var database: AppDatabase

        fun getDB() : AppDatabase {
            return database as AppDatabase
        }
    }

    fun getDB():AppDatabase{
        return database
    }
}