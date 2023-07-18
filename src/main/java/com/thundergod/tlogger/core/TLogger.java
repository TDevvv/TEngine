package com.thundergod.tlogger.core;
import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tengine.util.TEController;
import com.thundergod.tlogger.util.TLColor;
import com.thundergod.tlogger.util.TLDate;
import com.thundergod.tlogger.util.TLLevel;
import com.thundergod.tutil.core.TUText;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
public class TLogger implements ITPart{
    public static String ENGINE_NAMESPACE = TLColor.PURPLE+"//TEngine"+TLColor.RESET;
    private boolean isAttached;
    private PrintStream out = TLEngineLogger.out();
    @Override
    public void init() {}
    @Override
    public void loop(float dt) {}
    @Override
    public void dispose() {}
    Class<?> _class;
    public TLogger(Class<?> _class){
        this._class = _class;
    }
    List<Class<?>> classList = new ArrayList<>();
    public String class_txt;
    String phase,state;
    public void env(){
        if (TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem()!=null){
            TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(this);
        }
    }
    public void setPhase(String phase) {
        this.phase = phase;
    }
    public void setState(TEController.State state){
        this.state = state.name();
    }
    public void log(String s, TLLevel level){
        String converted_class_l = TUText.convertClassListToUsableString(classList);
        if (converted_class_l.equals("")){
            converted_class_l = "/non-class_inherit";
        }
        class_txt = converted_class_l+"::"+_class.getSimpleName();
        isAttached = TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().getComponentList().contains(this);
        if (isAttached){
            if (state==null){
                state = "";
            }
            String date = level.date+TUText.setBracket(TLDate.getConsoleDate(),"{","}")+ TLColor.RESET;
            String namespace = TLogger.ENGINE_NAMESPACE;
            String message = level.text+s+TLColor.RESET;
            String txt_lv = "";
            if (level.level_upper_case){
                txt_lv = level.level+level.toString()+TLColor.RESET;
            }else{
                txt_lv = level.level+level.toString().toLowerCase(Locale.ENGLISH)+TLColor.RESET;
            }
            txt_lv = TUText.setBracketWithColor(txt_lv+"?"+state,"[","]",level.bracket_etc);
            out.println(date+" -> "+namespace+class_txt+": "+txt_lv+" "+message);
        }else{
            TEngine.ENGINE_LOGGER.log("please attach logger to environment.",TLLevel.ERROR);
        }
    }
    public void log(String s, TLLevel.TLLevelCustom level){
        String converted_class_l = TUText.convertClassListToUsableString(classList);
        if (converted_class_l.equals(" ")){
            converted_class_l = "/non-class_inherit";
        }
        class_txt = converted_class_l+"::"+_class.getName();
        isAttached = TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().getComponentList().contains(this);
        if (isAttached){
            if (state==null){
                state="";
            }
            String date = level.date+TUText.setBracket(TLDate.getConsoleDate(),"{","}")+ TLColor.RESET;
            String namespace = TLogger.ENGINE_NAMESPACE;
            String message = level.text+s+TLColor.RESET;
            String txt_lv = "";
            if (level.level_upper_case){
                txt_lv = level.level+level.toString()+TLColor.RESET;
            }else{
                txt_lv = level.level+level.toString().toLowerCase(Locale.ENGLISH)+TLColor.RESET;
            }
            txt_lv = TUText.setBracketWithColor(txt_lv+"?"+state,"[","]",level.bracket_etc);
            out.println(date+" -> "+namespace+class_txt+": "+txt_lv+" "+message);
        }else{
            TEngine.ENGINE_LOGGER.log("please attach logger to environment.",TLLevel.ERROR);
        }
    }
    public void fatal(String message){
        log(message,TLLevel.FATAL);
    }
    public void fatalE(String message){
        fatal(message);
        throw new RuntimeException();
    }
    public void error(String message){
        log(message,TLLevel.ERROR);
    }
    public void warning(String message){
        log(message,TLLevel.WARNING);
    }
    public void debug(String message){
        log(message,TLLevel.DEBUG);
    }
    public void info(String message){
        log(message,TLLevel.INFO);
    }
    public void lifecycle(String message){
        log(message,TLLevel.LIFECYCLE);
    }
    public void custom(String message, TLLevel.TLLevelCustom custom){
        log(message,custom);
    }
    public void attachTo(TLogger logger){
        classList.add(logger._class);
    }
    public void objectCreate(Class<?> object_class,String object_name){
        info(TUText.setCurlyBracket("create")+":"+TUText.setRectBracket("object="+object_class.getSimpleName())+": "+object_name+" ;");
    }
    public <T extends Object>void nullCheck(T object){
        if (object==null){
            fatal("requested "+TUText.setCurlyBracket("null/*->full")+" object is null it may be cause because wrong key or method.");
        }
    }
}
