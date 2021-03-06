package demo;



import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.ModelRecordElResolver;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;


public class DemoConfig extends JFinalConfig {  
    public void configConstant(Constants me) {   
    	PropKit.use("a_little_config");
    	me.setDevMode(PropKit.getBoolean("devMode", false));
    }  
  
    public void configRoute(Routes me) { 
    	
    	me.add("/",IndexController.class);
    	me.add("/company", CompanyController.class,"/company"); 
    	
    }  
    public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
    public void configPlugin(Plugins me) { 
    	
    		DruidPlugin druidPlugin = createDruidPlugin();
			me.add(druidPlugin);
			// 配置ActiveRecord插件
			ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
    		arp.addMapping("company_news_data","id", Company.class);	// 映射company_news_data 表到 Company模型
    		me.add(arp);
    }  
  
    public void configInterceptor(Interceptors me) {  
    }  
    public void configHandler(Handlers me) {  
    }

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		me.addSharedFunction("/company/common/_layout.html");
		//me.addSharedFunction("/company/common/_paginate.html");
		me.addSharedFunction("/company/common/company_info.html");
		
	}  
}  
