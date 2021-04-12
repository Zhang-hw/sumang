package com.sibd.sumang.dao.impl;

import com.sibd.sumang.dao.RouteDao;
import com.sibd.sumang.pojo.BigPic;
import com.sibd.sumang.pojo.Fav;
import com.sibd.sumang.pojo.Route;
import com.sibd.sumang.util.JdbcUtils;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteDaoImpl implements RouteDao{
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public int findCount(String cid,String rname) {

        StringBuilder sb=new StringBuilder("select count(1) from tab_route where 1=1 ");
        List<String> list=new ArrayList<>();

        if (!cid.equals("0")){
            sb.append(" and cid = ? ");
            list.add(cid);
        }


        if (!StringUtils.isEmpty(rname)){
            sb.append(" and rname like ?");
            list.add("%"+rname+"%");
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), Integer.class, list.toArray());
        return count;
    }

    @Override
    public List<Route> findByPage(String cid, int currentPage, int pageSize,String rname) {
        StringBuilder sb = new StringBuilder(" select * from tab_route where 1=1 ");
        List params = new ArrayList();
        if (!cid.equals("0")){
            sb.append( " and cid = ? ");
            params.add(cid);
        }

        if(!(rname==null||rname=="")) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ?,?");
        params.add((currentPage-1)*10);
        params.add(pageSize);
//        System.out.println(sb.toString());
//        System.out.println(Arrays.toString(params.toArray()));
        List<Route> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        return list;
    }

    @Override
    public Route findRouteById(String rid) {
        Route route = jdbcTemplate.queryForObject("select * from tab_route where rid=?",
                new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }

    @Override
    public void updateCount(String rid) {
        jdbcTemplate.update("update tab_route set count = count+1 where rid=? ",rid);
    }

    @Override
    public List<Route> findFavoriteRank() {
        List<Route> list = jdbcTemplate.query("select * from tab_route order by count desc", new BeanPropertyRowMapper<Route>(Route.class));
        return list;
    }

    /**
     * 收藏排行榜查询数量
     * @param low
     * @param high
     * @param rname
     * @return
     */
    @Override
    public int favFindCount(String low, String high, String rname) {
        StringBuilder sb=new StringBuilder("select count(1) from tab_route where 1=1 ");
        List<String> list=new ArrayList<>();

        if (!StringUtils.isEmpty(rname)){
            sb.append(" and rname like ?");
            list.add("%"+rname+"%");


        }
        if((!StringUtils.isEmpty(low))&&(!StringUtils.isEmpty(high))){
            sb.append(" and price between ? and ?");
            list.add(low);
            list.add(high);
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), Integer.class, list.toArray());
        return count;
    }

    /**
     * 收藏排行榜分页
     * @param low
     * @param high
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> favFindByPage(String low, String high, int currentPage, int pageSize, String rname) {
        StringBuilder sb = new StringBuilder(" select * from tab_route where 1=1 ");
        List params = new ArrayList();

        if (!StringUtils.isEmpty(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        if((!StringUtils.isEmpty(low))&&(!StringUtils.isEmpty(high))){
            sb.append(" and price between ? and ?");
            params.add(low);
            params.add(high);
        }
        sb.append( " order by count desc");
        sb.append( " limit ?,?");
        params.add((currentPage-1)*10);
        params.add(pageSize);

//        System.out.println(sb.toString());
//        System.out.println(Arrays.toString(params.toArray()));
        List<Route> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        return list;
    }


    @Override
    public int myFavFindCount(String uid) {
        Integer count = jdbcTemplate.queryForObject("select count(1) from tab_favorite where uid=?", Integer.class, uid);
        return count;
    }

    @Override
    public List<Route> myFavFindByPage(List<Fav> favs, int currentPage) {
        List<Route> routes = new ArrayList<>() ;
        for (Fav fav:favs
             ) {
            Route route = jdbcTemplate.queryForObject("select * from tab_route where rid=?", new BeanPropertyRowMapper<Route>(Route.class), fav.getRid());
            routes.add(route);
        }
        return routes;
    }

    @Override
    public List<Fav> findRids(String uid) {
        List<Fav> favs = jdbcTemplate.query("select * from tab_favorite where uid=?", new BeanPropertyRowMapper<Fav>(Fav.class), uid);
        return  favs;
    }

    @Override
    public List<Route> findPopularity() {
        List<Route> list = jdbcTemplate.query("SELECT * FROM tab_route ORDER BY COUNT DESC LIMIT 0,4", new BeanPropertyRowMapper<Route>(Route.class));

        return list;
    }

    @Override
    public List<Route> findNewest() {
        List<Route> list = jdbcTemplate.query("SELECT * FROM tab_route ORDER BY rdate DESC limit 0,4", new BeanPropertyRowMapper<Route>(Route.class));
        return list;
    }

    @Override
    public List<Route> findTheme() {
        List<Route> list = jdbcTemplate.query("SELECT * FROM tab_route WHERE isThemeTour=1", new BeanPropertyRowMapper<Route>(Route.class));
        return list;
    }

    @Override
    public List<Route> findHot(String rname) {
//        System.out.println(rname);
//        String a ="\'%"+rname+"%\'";
//        if(!(rname==null||rname.equals(""))){
//            return jdbcTemplate.query("select * from tab_route where rname like ? order by count desc limit 0,5 ", new BeanPropertyRowMapper<Route>(Route.class),a);
//        }else {
//            return jdbcTemplate.query("select * from tab_route where 1=1 order  by count desc limit 0,5", new BeanPropertyRowMapper<Route>(Route.class));
//        }
        StringBuilder sb = new StringBuilder(" select * from tab_route where 1=1 ");
        List params = new ArrayList();
        if (!(rname==null) && !(rname.equals(""))){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
       sb.append(" order by count desc limit 0,5");
        System.out.println(sb.toString());
        System.out.println(Arrays.toString(params.toArray()));
        List<Route> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        return list;
    }

    @Override
    public List<BigPic> findBigPic() {
        List<BigPic> list = jdbcTemplate.query("select * from tab_bigpic", new BeanPropertyRowMapper<BigPic>(BigPic.class));
        return list;
    }


    public List<Route> findChina() {
        List<Route> list=new ArrayList<>();
        int random= (int) (Math.random()*505+1);
        for (int i =random; i<=random+5;i++) {
            Route route = jdbcTemplate.queryForObject("select * from tab_route where rid=?", new BeanPropertyRowMapper<Route>(Route.class), i);
            list.add(route);
        }
        return list;
    }

}



