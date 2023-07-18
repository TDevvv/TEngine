package com.thundergod.tdata_helper.file_system;

import com.thundergod.tdata_helper.file_system.abstracts.Pattern;
import com.thundergod.tdata_helper.file_system.annotations.dtype.DType;
import com.thundergod.tdata_helper.file_system.annotations.id.ID;
import com.thundergod.tdata_helper.file_system.annotations.reader_settings.IOSettings;
import com.thundergod.tdata_helper.file_system.annotations.reader_settings.ReaderSettingsHandler;
import com.thundergod.tdata_helper.file_system.data.TUDataFile;


import java.io.File;
import java.io.FileNotFoundException;


import java.util.*;

public class TUFileReader {
    TUDataFile FILE;

    public void setFILE(TUDataFile FILE) {
        this.FILE = FILE;
    }

    public TUDataFile getFILE() {
        return FILE;
    }

    Scanner SCANNER;
    static Class Class;
    boolean enable_mod;

    private int word_ct;
    private int line_ct;
    protected Count counter;
    protected SyntaxPatterned syntaxPatterned;
    public String getUnDottedPath(String FILE_NAME){
        String RETURN = FILE_NAME.substring(0,FILE_NAME.lastIndexOf("."));
        return RETURN;

    }
    public TUFileReader reset(TUFileReader INSTANCE,boolean enable_mod,String FILE_PATH){
        Class c = Class;

        INSTANCE = TUFileReader.create(TUFileHandler.createDataFile(FILE_PATH,c),c);
        INSTANCE.setup(enable_mod);
        return INSTANCE;
    }

    public void setSCANNER(Scanner SCANNER) {
        this.SCANNER = SCANNER;
    }

    public Scanner getSCANNER() {
        return SCANNER;
    }

    public void setSyntaxPatterned(SyntaxPatterned syntaxPatterned) {
        this.syntaxPatterned = syntaxPatterned;
    }

    public SyntaxPatterned getSyntaxPatterned() {
        return syntaxPatterned;
    }

    public void setCounter(Count counter) {
        this.counter = counter;
    }

    public Count getCounter() {
        return counter;
    }


    public void setEnable_mod(boolean enable_mod) {
        this.enable_mod = enable_mod;
    }

    public boolean isEnable_mod() {
        return enable_mod;
    }

    public void setClass(java.lang.Class aClass) {
        Class = aClass;
    }
    public enum ReaderMode{
        DETAILED,
        DEFAULT,
        SYNTAX,
        EMPTY
    }
    public static TUFileReader create(TUDataFile FILE,Class c){
        Class  = c;
        try {
            return new TUFileReader(FILE,new Scanner(FILE));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private TUFileReader(TUDataFile FILE,Scanner SCANNER){
        this.FILE = FILE;
        this.SCANNER = SCANNER;
    }




    public String read(){
        //word_ct++;
        if (SCANNER.hasNext()){
            if (Class!=null&&enable_mod){
                switch (ReaderSettingsHandler.handle(Class).getMOD()){
                    case DEFAULT -> {
                        return SCANNER.next();
                    }
                    case DETAILED -> {
                        String S = SCANNER.next();
                        word_ct++;
                        System.out.println("\n");
                        System.out.println("//BEGIN//>"+S.toUpperCase());
                        System.out.println("word ct : "+word_ct);
                        System.out.println("word : "+S);
                        System.out.println("word length : "+S.length());
                        System.out.println("word hash :"+S.hashCode());
                        System.out.println("word continue ?= :"+SCANNER.hasNext());
                        System.out.println("word scan date = :"+new Date());
                        System.out.println("//END//>"+S.toUpperCase());
                        System.out.println("\n");
                        return S;
                    }
                    case SYNTAX -> {
                        return "use syntax class.";
                    }
                    default -> {
                        return "";
                    }
                }
            }else{
                return "";
            }
        }
        else {
            return "";
        }
    }
    public int readInt(){
        if (SCANNER.hasNextInt()){
            return SCANNER.nextInt();
        }else{
            return 0;
        }

    }
    public String readLine(){
        if (SCANNER.hasNextLine()){
            if (Class!=null&&enable_mod){
                switch (ReaderSettingsHandler.handle(Class).getMOD()){
                    case DEFAULT -> {
                        return SCANNER.nextLine();
                    }
                    case DETAILED -> {
                        String S = SCANNER.nextLine();
                        line_ct++;
                        System.out.println("\n");
                        System.out.println("//BEGIN//>"+S.toUpperCase());
                        System.out.println("line ct : "+line_ct);
                        System.out.println("line : "+S);
                        System.out.println("line length : "+S.length());
                        System.out.println("line hash :"+S.hashCode());
                        System.out.println("line continue ?= :"+SCANNER.hasNextLine());
                        System.out.println("line scan date = :"+new Date());
                        System.out.println("//END//>"+S.toUpperCase());
                        System.out.println("\n");
                        return S;
                    }
                    default -> {
                        return "";
                    }
                }
            }else{
                return "";
            }
        }else{
            return "";
        }
    }

    public String readAll(){
        int j =Count.countWord(FILE);
        StringBuilder STR = new StringBuilder();
        for (int i = 0; i < j; i++) {
            STR.append(read()).append(" ");
        }
        return STR.toString();
    }
    public String readWithNewLine(){
        int j = Count.countWord(FILE);
        StringBuilder STR = new StringBuilder();
        for (int i = 0; i < j; i++) {
            STR.append(readLine()).append("\n");
        }
        return STR.toString();
    }
    public String[] readAllArray(){
        int j =Count.countWord(FILE);
        //System.out.println(j);
        List<String> S = Arrays.asList(readAll().split(" "));

        String[] STR = new String[j];
        for (int k = 0; k < j; k++) {
           // System.out.println(S.toString());
            STR[k] = S.get(k);
        }
        return STR;
    }
    public List<String> readAllList(){
        List<String> RETURN = new ArrayList<>();
        Collections.addAll(RETURN, readAllArray());
        return RETURN;
    }
    public HashMap<Integer,String> readAllHashMap(){
        HashMap<Integer,String> RETURN = new HashMap<>();
        int i = 0;
        for (String s:
             readAllList()) {
            i++;
            RETURN.put(i,s);
        }
        return RETURN;
    }

    public String readAllLines(){
        /*
        int j =Count.countWord(FILE);
        //System.out.println(j);
        List<String> S = Arrays.asList(readAll().split(" "));

        String[] STR = new String[j];
        for (int k = 0; k < j; k++) {
            // System.out.println(S.toString());
            STR[k] = S.get(k);
        }
        return STR;

         */
        int j =Count.countLine(FILE);
        StringBuilder STR = new StringBuilder();
        for (int i = 0; i < j; i++) {
            STR.append(readLine()).append(" ");
        }
        return STR.toString();
    }
    public String[] readAllLinesArray(){
        int j =Count.countLine(FILE);
        String[] STR = new String[j];
        List<String> STRINGS = new ArrayList<>();
        for (int k = 0; k < j; k++) {
            STRINGS.add(readLine());
        }
        for (int i = 0; i < j; i++) {
            STR[i] = STRINGS.get(i);
        }
        return STR;
    }
    public List<String> readAllLinesList(){
        List<String> RETURN = new ArrayList<>();
        Collections.addAll(RETURN, readAllLinesArray());
        return RETURN;
    }
    public HashMap<Integer,String> readAllLinesHashMap(){
        HashMap<Integer,String> RETURN = new HashMap<>();
        int i = 0;
        for (String s:
                readAllLinesList()) {
            i++;
            RETURN.put(i,s);
        }
        return RETURN;
    }
    @Deprecated()
    public void end(TUFileReader INSTANCE, TUDataFile NEW_PATH){
        try {
            INSTANCE = new TUFileReader(NEW_PATH,new Scanner(NEW_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> readPatternIntegerList(String PATTERN){
        List<Integer> INT_LIST = new ArrayList<>();
        loop:
        for (int i = 0; i < Count.countLine(FILE)*2; i++) {
            if (read().equals(PATTERN)){
                INT_LIST.add(Integer.parseInt(read()));
            }else {
                read();
            }
        }
        return INT_LIST;
    }
    class Count{
      public static int countWord(String PATH){
          try {
              Scanner scr = new Scanner(new File(PATH));
              int i = 0;
              do {
                  i++;
                  scr.next();
              }while (scr.hasNext());
              return i;
          } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
          }
      }
        public static int countWord(TUDataFile PATH){
            try {
                Scanner scr = new Scanner(PATH);
                int i = 0;
                do {
                    i++;
                    scr.next();
                }while (scr.hasNext());
                return i;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
      public static int countLine(String PATH){
          try {
              Scanner scr = new Scanner(new File(PATH));
              int i = 0;
              do {
                  i++;
                  scr.nextLine();
              }while (scr.hasNextLine());
              return i;
          } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
          }
      }
        public static int countLine(TUDataFile PATH){
            try {
                Scanner scr = new Scanner(PATH);
                int i = 0;
                do {
                    i++;
                    scr.nextLine();
                }while (scr.hasNextLine());
                return i;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setup(boolean enable_mod){
        Class c = Class;
        if (ReaderSettingsHandler.handle(c).getMOD()==ReaderMode.SYNTAX){
            setSyntaxPatterned(SyntaxPatterned.create(FILE,SCANNER));
            setClassSyntax(c);
            setReaderModeSyntax(ReaderSettingsHandler.handle(c).getSYNTAX_MOD());
            setEnable_mod(enable_mod);
            setClass(c);
        }else{
            setEnable_mod(enable_mod);
            setClass(c);
        }

    }
    public List<String> readWithValuePattern(String PATTERN){
        return  getSyntaxPatterned().readWithValuePattern(PATTERN);
    }
    public String readWithValuePatterPlain(String PATTERN){
        String RESULT  ="";
        for (int i = 0; i < Count.countLine(FILE.getAbsolutePath())*2; i++) {
            if (read().equals(PATTERN)){
                RESULT = read();
            }else{
                read();
            }
        }
        return RESULT;
    }
    public int readWithValuePatterPlainInteger(String PATTERN){
        int RESULT  =0;
        boolean pattern_founded = false;
        for (int i = 0; i < Count.countLine(FILE)*2; i++) {
          String readed = read();
          if (readed.equals(PATTERN)){
              pattern_founded = true;
          }
          if (pattern_founded){
              RESULT = Integer.parseInt(read());
          }
        }
        return RESULT;
    }
    public HashMap<Integer,String> readWithValuePatternHashMapString(String PATTERN){
        List<String> LIST = readWithValuePattern(PATTERN);
        HashMap<Integer,String> HASHMAP = new HashMap<>();
        int i = 0;
        for (String s:
             LIST) {
            i++;
            HASHMAP.put(i,s);
        }
        return HASHMAP;
    }
    public List<Integer> readWithValuePatternInteger(String PATTERN){
        return getSyntaxPatterned().readWithValueInteger(PATTERN);
    }
    public List<String> readWithStringValuePattern(String PATTERN){
        List<String> STR_LIST = new ArrayList<>();
        for (int i = 0; i < Count.countLine(FILE)*2; i++) {
            if (read().equals(PATTERN)){
                STR_LIST.add(read());
            }else {
                read();
            }
        }
        return STR_LIST;
    }

    public HashMap<Integer,Integer> readWithValuePatternHashMap(String PATTERN){
        HashMap<Integer,Integer> HASH = new HashMap<>();
        int i = 0;
        for (Integer value:
             readWithValuePatternInteger(PATTERN)) {
            i++;
            HASH.put(i,value);
        }
        return HASH;
    }
    public List<String> readWithValuePatternTest(String PATTERN){
        List<String> RETURN = new ArrayList<>();
        for (int i = 0; i < Count.countLine(FILE.getAbsolutePath()); i++) {
            String pattern = read();
            if (ReaderSettingsHandler.handle(Class).getSYNTAX_MOD()==ReaderMode.DETAILED){
                System.out.println("pattern : "+pattern);
            }
            if (pattern.equals(PATTERN)){
                String value = read();
                if (ReaderSettingsHandler.handle(Class).getSYNTAX_MOD()==ReaderMode.DETAILED){
                    System.out.println("value :"+ value);
                }
                RETURN.add(value);
            }else{
                read();
            }
        }
        return RETURN;
    }
    public void dataFileUtilPatterned(String PATTERN){
        int counted = Count.countWord(this.FILE);
        System.out.println(counted);
        for (int i = 0; i < Count.countWord(this.FILE); i++) {
            if (SCANNER.next().equals(PATTERN)){
                System.out.println(SCANNER.next());
            }
        }
    }
    public void setClassSyntax(Class c){
        getSyntaxPatterned().setClass(c);
    }
    public void setReaderModeSyntax(ReaderMode MOD){
        getSyntaxPatterned().setREADER_MODE(MOD);
    }
    public ReaderMode getReaderModeSyntax(){
        return getSyntaxPatterned().getREADER_MODE();
    }
     static class SyntaxPatterned extends TUFileReader{
        private File FILE;
        private Scanner SCANNER;
        private Class Class;
        private ReaderMode READER_MODE;

        public void setREADER_MODE(ReaderMode READER_MODE) {
             this.READER_MODE = READER_MODE;
         }

        public ReaderMode getREADER_MODE() {
             return READER_MODE;
         }

         @Override
         public void setClass(java.lang.Class aClass) {
             Class = aClass;
         }


         public static SyntaxPatterned create(TUDataFile file , Scanner SCANNER){
            SyntaxPatterned PATTERNED = new SyntaxPatterned(file,SCANNER);
            PATTERNED.FILE = file;
            PATTERNED.SCANNER = SCANNER;
            return PATTERNED;
        }
        public List<String> readWithValuePattern(String PATTERN){
            List<String> RETURN = new ArrayList<>();
            for (int i = 0; i < Count.countLine(FILE.getAbsolutePath()); i++) {
                String pattern = read();
                if (ReaderSettingsHandler.handle(Class).getSYNTAX_MOD()==ReaderMode.DETAILED){
                    System.out.println("pattern : "+pattern);
                }
                if (pattern.equals(PATTERN)){
                    String value = read();
                    if (ReaderSettingsHandler.handle(Class).getSYNTAX_MOD()==ReaderMode.DETAILED){
                        System.out.println("value :"+ value);
                    }
                    RETURN.add(value);
                }else{
                    read();
                }
            }
            return RETURN;
        }
        public HashMap<Integer,String> readWithValueWithHashMap(String PATTERN){
            List<String> STR_LIST =  readWithValuePattern(PATTERN);
            HashMap<Integer,String> STR_HASH = new HashMap<>();
            int i = 0;
            for (String s:
                 STR_LIST) {
                i++;
                STR_HASH.put(i,s);
            }
            return STR_HASH;
        }

        public List<Integer> readWithValueInteger(String PATTERN){
            List<Integer>  LIST = new ArrayList<>();

            for (int i = 0; i < Count.countLine(FILE.getAbsolutePath())*2; i++) {
                if (read().equals(PATTERN)){
                    int rd = readInt();
                    LIST.add(rd);
                }else{
                    readInt();
                }
            }


            return LIST;
        }

         @Override
         public String read() {
             if (SCANNER.hasNext()){
                 return SCANNER.next();
             }else{
                 return "";
             }
         }

         public SyntaxPatterned creates(TUDataFile FILE, Scanner SCANNER){
            this.FILE = FILE;
            this.SCANNER = SCANNER;
            return new SyntaxPatterned(FILE,SCANNER);
        }
        private SyntaxPatterned(TUDataFile FILE, Scanner SCANNER) {
            super(FILE, SCANNER);
        }
    }
    public void run(){
        getSyntaxPatterned().run();
    }
    public void pattern(){
        PatternConfig patternConfig = new PatternConfig();
        PatternConfig.setRD(TUFileReader.create(new TUDataFile(FILE.getAbsolutePath()),Class));
        patternConfig.pattern();
    }
    @ID(ID = 1)
    static class PatternConfig extends Pattern {
        @DType(file_extension = ".tdata",show_result = true)
        @IOSettings(MODE = ReaderMode.SYNTAX,SYNTAX_MODE = ReaderMode.DETAILED)
        public static TUFileReader rd;

        public static void setRD(TUFileReader RD) {
            PatternConfig.rd = RD;
        }

        @Override
        public void run() {
            super.run();
        }

        public static TUFileReader getRD() {
            return rd;
        }
        @Override
        public void pattern() {
            reset();
            String FILENAME = rd.readWithValuePatterPlain("file_name:");
            reset();
            String FILE_EXTENSION = rd.readWithValuePatterPlain("file_extension:");
            reset();
            String SHOW_RESULT = rd.readWithValuePatterPlain("show_result:");
            boolean rtrn;
            if (SHOW_RESULT.equals("true")){
                rtrn = true;
            }else if (SHOW_RESULT.equals("false")){
                rtrn = false;
            }else{
                rtrn = false;
            }
            reset();
            String READER_MODE = rd.readWithValuePatterPlain("reader_mode:");
            ReaderMode READER_MODE_ENUM;
            if (READER_MODE.equals(ReaderMode.SYNTAX.toString())){
                READER_MODE_ENUM = ReaderMode.SYNTAX;
            } else if (READER_MODE.equals(ReaderMode.DETAILED.toString())) {
                   READER_MODE_ENUM = ReaderMode.DETAILED;
            }else{
                READER_MODE_ENUM = ReaderMode.EMPTY;
            }
            reset();
            String SYNTAX_READER_MODE = rd.readWithValuePatterPlain("syntax_reader_mode:");
            ReaderMode SYNTAX_READER_MODE_ENUM;
            if (SYNTAX_READER_MODE.equals(ReaderMode.DETAILED.toString())){
                SYNTAX_READER_MODE_ENUM = ReaderMode.DETAILED;
            }else{
                SYNTAX_READER_MODE_ENUM = ReaderMode.EMPTY;
            }
            reset();
           // System.out.println("file_name -->"+FILENAME+" file_extension -->"+FILE_EXTENSION+" show_result --->"+rtrn+" reader_mode --->"+READER_MODE_ENUM.toString()+" syntax_reader_mode --->"+SYNTAX_READER_MODE_ENUM.toString());

        }

        @Override
        public TUFileReader instance(TUFileReader reader) {
            return rd;
        }
        public static void reset(){
            rd = rd.reset(rd,true,"test");
        }
    }
    public static class PatternCreator<T,K>{

        T pattern;
        K value;
        @DType(file_extension = ".tdata",show_result = true)
        @IOSettings(MODE = ReaderMode.SYNTAX,SYNTAX_MODE = ReaderMode.DETAILED)
        public static TUFileReader rd;

        public static String PATH;

        public PatternCreator(TUFileReader INSTANCE){
            rd = INSTANCE;
        }

        public PatternCreator<T, K> setupVariables(Object pattern,Object value){
            this.pattern =(T)pattern;
            this.value = (K)value;
            return this;
        }
        public  PatternCreator<T, K> setPATH(String PATH) {
            PatternCreator.PATH = PATH;
            return this;
        }
        public void setup(TUFileReader READER,String PATH,String PATTERN,Object VALUE){

            setupVariables("test:",VALUE);
            setRD(READER);
            setPATH("test");
        }

        public static String getPATH() {
            return PATH;
        }

        public static TUFileReader getRd() {
            return rd;
        }
        public  PatternCreator<T, K> setRD(TUFileReader RD) {
            PatternConfig.rd = RD;
            return this;
        }
        public static TUFileReader getRD() {
            return rd;
        }
        public PatternResultField go(){
            reset();
            Object VALUE="";
            if(pattern instanceof String){
                if (value instanceof Integer){
                    VALUE = rd.readWithValuePatterPlainInteger(pattern.toString());
                    //System.out.println(VALUE);
                    value =(K) VALUE;
                }else {
                    VALUE = rd.readWithValuePatterPlain((String) pattern);
                }
            }else if (pattern instanceof Integer){
               VALUE = rd.readWithValuePatterPlain(pattern.toString());
            }else{
                System.out.println("this type is not cnsrd.");
            }
            if (ReaderSettingsHandler.handle(rd.getClass()).getSYNTAX_MOD()==ReaderMode.DETAILED){
                System.out.println("pattern: "+pattern+" value:"+VALUE);
            }
            return new PatternResultField(pattern,VALUE);
        }
        public List<PatternResultField> listedGo(){
            reset();
            Object VALUE;
            List<PatternResultField> RESULT_FIELD_PATTERN = new ArrayList<>();
            if (pattern instanceof String){
                if (value instanceof Integer){
                    List<Integer>  INT_LIST= rd.readPatternIntegerList(pattern.toString());
                    for (Integer integer:
                         INT_LIST) {
                        RESULT_FIELD_PATTERN.add(new PatternResultField(pattern,integer));
                    }
                }else {
                    List<String> STR_LIST = rd.readWithStringValuePattern((String) pattern);
                    for (String s:
                         STR_LIST) {
                        RESULT_FIELD_PATTERN.add(new PatternResultField(pattern,s));
                    }
                }
            }else {
                System.out.println("this type is not cnsrd.");
            }
            return RESULT_FIELD_PATTERN;

        }
        public  void reset(){
            rd = rd.reset(rd,true,PATH);
        }

    }
    public PatternCreator<?,?> createPattern(String PATTERN,String VALUE,Object PATTERN_VAL,String PATH,TUFileReader INSTANCE){

           switch (VALUE){
               case "int" ->{
                   PatternCreator<String,Integer> NEW_PATTERN = new PatternCreator<>(INSTANCE);
                   NEW_PATTERN.setupVariables(PATTERN_VAL,0);
                   NEW_PATTERN.setPATH(PATH);
                   NEW_PATTERN.setRD(INSTANCE);
                   return NEW_PATTERN;
               }
               case "string" ->{
                   PatternCreator<String,String> NEW_PATTERN = new PatternCreator<>(INSTANCE);
                   NEW_PATTERN.setupVariables(PATTERN_VAL,VALUE);
                   NEW_PATTERN.setPATH(PATH);
                   NEW_PATTERN.setRD(INSTANCE);
                   return NEW_PATTERN;
               }
               default -> {
                   return null;
               }
           }
    }
    public void test(){
        PatternCreator<String,Integer> TEST = new PatternCreator<>(this);
        TEST.setRD(this);
        TEST.setPATH("test");
        TEST.pattern = "test:";
        TEST.value = 1;
        TEST.go();
    }
    public PatternCreator<?,?> createPattern(ObjectPattern PATTERN){

        if (PATTERN.equals("string")||PATTERN.VALUE.equals("string")){
            PatternCreator<String,String> newPattern =  new PatternCreator<String,String>(PATTERN.INSTANCE);
            newPattern.setRD(PATTERN.INSTANCE);
            newPattern.setupVariables(PATTERN.PATTERN_VAL.toString(),null);
            newPattern.setPATH(PATTERN.PATH);
            return newPattern;
        }else if (PATTERN.VALUE.equals("integer")){
            PatternCreator<String,Integer> newPattern =  new PatternCreator<String,Integer>(PATTERN.INSTANCE);
            newPattern.setRD(PATTERN.INSTANCE);
            newPattern.setupVariables(PATTERN.PATTERN_VAL.toString(),null);
            newPattern.setPATH(PATTERN.PATH);
            return newPattern;
        }else{
            return null;
        }

    }

    public record ObjectPattern(String PATTERN,String VALUE,Object PATTERN_VAL,String PATH,TUFileReader INSTANCE){
    }
    public  record PatternResultField(Object PATTERN,Object Value) {

    }
    public String readPatternedWithLine(String PATTERN){
        String RETURN = "";
        do {
            String READED = readLine();
            if (READED.contains(PATTERN)){
                RETURN = READED.substring(PATTERN.length());
            }
        }while (SCANNER.hasNextLine());
        return RETURN;
    }
    public String readPatternedWithLineOne(String PATTERN){
        String RETURN = "";

            String READED = readLine();
            if (READED.contains(PATTERN)){
                RETURN = READED.substring(PATTERN.length());
            }

        return RETURN;
    }
    public List<String> readPatternedWithLineList(String PATTERN){
        List<String> RETURN_LIST = new ArrayList<>();
        do {
            RETURN_LIST.add(readPatternedWithLineOne(PATTERN));
        }while (SCANNER.hasNextLine());
        return RETURN_LIST;
    }
    public List<String> readPatternedWithLineListValues(String PATTERN){
        List<String> VALUE = readPatternedWithLineList(PATTERN);
        List<String> RETURN_VALUE = new ArrayList<>();

        for (String s:
             VALUE) {
            List<String> Dps_values =Arrays.asList(s.split(" "));
            for (String F:
                 Dps_values) {
                RETURN_VALUE.add(F);
            }
        }
        return RETURN_VALUE;
    }
    public List<String> readPatternedWithLineListValuesOne(String PATTERN){
        List<String> RETURN = new ArrayList<>();
        for (String s:
             readPatternedWithLineOne(PATTERN).split(" ")) {
            RETURN.add(s);
        }
        return RETURN;
    }
    public void set_to_syntax_ort(File FILE,Scanner SCANNER){
        setSyntaxPatterned(new SyntaxPatterned(new TUDataFile(FILE.getAbsolutePath()),SCANNER));
    }
    boolean continues = false;
    public void begin(){
        continues = true;
    }
    public void _end(){
        continues = false;
    }
    public static class ReaderLoggerUtils{




    }
}

