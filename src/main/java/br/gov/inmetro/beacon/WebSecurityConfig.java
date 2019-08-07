package br.gov.inmetro.beacon;

//@EnableWebSecurity
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//            http.csrf().disable().authorizeRequests()
//                    .antMatchers("/css/**", "/images/**","/js/**","/certificate/**").permitAll()
//                    .antMatchers(HttpMethod.POST, "/rest/record").authenticated()
//                    .and().httpBasic()
//                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//
//}