package volk.gantt.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
@EnableSocial
@PropertySource("classpath:social-cfg.properties")
public class SocialConfig implements SocialConfigurer {
    
    private boolean autoSignUp = false;

    @Autowired
    private DataSource dataSource;

    // @Autowired
    // TODO: private UserRepo userRepo;

    // env: read from social-cfg.propoerties file
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        
        try {
            this.autoSignUp = Boolean.parseBoolean(env.getProperty("social.auto-signup"));
        } catch (Exception e) {
            this.autoSignUp = false;
        }
        
        // Twitter
        TwitterConnectionFactory tFactory = new TwitterConnectionFactory(
                env.getProperty("twitter.consumer.key"),
                env.getProperty("twitter.consumer.secret"));
        cfConfig.addConnectionFactory(tFactory);

        // Facebook
        FacebookConnectionFactory ffactory = new FacebookConnectionFactory(
                env.getProperty("facebook.app.id"), 
                env.getProperty("facebook.app.secret"));
        ffactory.setScope(env.getProperty("facebook.scope"));
        cfConfig.addConnectionFactory(ffactory);

        // Linkedin
        LinkedInConnectionFactory lfactory = new LinkedInConnectionFactory(
                env.getProperty("linkedin.consumer.key"), 
                env.getProperty("linkedin.consumer.secret"));
        lfactory.setScope(env.getProperty("linkedin.scope"));
        cfConfig.addConnectionFactory(lfactory);
        
        // Google
        GoogleConnectionFactory gfactory = new GoogleConnectionFactory(
                env.getProperty("google.client.id"),
                env.getProperty("google.client.secret"));
        gfactory.setScope(env.getProperty("google.scope"));
        cfConfig.addConnectionFactory(gfactory);
    }

    // The UserIdSource determine the userId of the user
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
