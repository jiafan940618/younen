package com.yn.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElecDataYearExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ElecDataYearExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateDtmIsNull() {
            addCriterion("create_dtm is null");
            return (Criteria) this;
        }

        public Criteria andCreateDtmIsNotNull() {
            addCriterion("create_dtm is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDtmEqualTo(Date value) {
            addCriterion("create_dtm =", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmNotEqualTo(Date value) {
            addCriterion("create_dtm <>", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmGreaterThan(Date value) {
            addCriterion("create_dtm >", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("create_dtm >=", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmLessThan(Date value) {
            addCriterion("create_dtm <", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmLessThanOrEqualTo(Date value) {
            addCriterion("create_dtm <=", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmIn(List<Date> values) {
            addCriterion("create_dtm in", values, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmNotIn(List<Date> values) {
            addCriterion("create_dtm not in", values, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmBetween(Date value1, Date value2) {
            addCriterion("create_dtm between", value1, value2, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmNotBetween(Date value1, Date value2) {
            addCriterion("create_dtm not between", value1, value2, "createDtm");
            return (Criteria) this;
        }

        public Criteria andDelIsNull() {
            addCriterion("del is null");
            return (Criteria) this;
        }

        public Criteria andDelIsNotNull() {
            addCriterion("del is not null");
            return (Criteria) this;
        }

        public Criteria andDelEqualTo(Integer value) {
            addCriterion("del =", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelNotEqualTo(Integer value) {
            addCriterion("del <>", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelGreaterThan(Integer value) {
            addCriterion("del >", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelGreaterThanOrEqualTo(Integer value) {
            addCriterion("del >=", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelLessThan(Integer value) {
            addCriterion("del <", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelLessThanOrEqualTo(Integer value) {
            addCriterion("del <=", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelIn(List<Integer> values) {
            addCriterion("del in", values, "del");
            return (Criteria) this;
        }

        public Criteria andDelNotIn(List<Integer> values) {
            addCriterion("del not in", values, "del");
            return (Criteria) this;
        }

        public Criteria andDelBetween(Integer value1, Integer value2) {
            addCriterion("del between", value1, value2, "del");
            return (Criteria) this;
        }

        public Criteria andDelNotBetween(Integer value1, Integer value2) {
            addCriterion("del not between", value1, value2, "del");
            return (Criteria) this;
        }

        public Criteria andDelDtmIsNull() {
            addCriterion("del_dtm is null");
            return (Criteria) this;
        }

        public Criteria andDelDtmIsNotNull() {
            addCriterion("del_dtm is not null");
            return (Criteria) this;
        }

        public Criteria andDelDtmEqualTo(Date value) {
            addCriterion("del_dtm =", value, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmNotEqualTo(Date value) {
            addCriterion("del_dtm <>", value, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmGreaterThan(Date value) {
            addCriterion("del_dtm >", value, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("del_dtm >=", value, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmLessThan(Date value) {
            addCriterion("del_dtm <", value, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmLessThanOrEqualTo(Date value) {
            addCriterion("del_dtm <=", value, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmIn(List<Date> values) {
            addCriterion("del_dtm in", values, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmNotIn(List<Date> values) {
            addCriterion("del_dtm not in", values, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmBetween(Date value1, Date value2) {
            addCriterion("del_dtm between", value1, value2, "delDtm");
            return (Criteria) this;
        }

        public Criteria andDelDtmNotBetween(Date value1, Date value2) {
            addCriterion("del_dtm not between", value1, value2, "delDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmIsNull() {
            addCriterion("update_dtm is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmIsNotNull() {
            addCriterion("update_dtm is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmEqualTo(Date value) {
            addCriterion("update_dtm =", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmNotEqualTo(Date value) {
            addCriterion("update_dtm <>", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmGreaterThan(Date value) {
            addCriterion("update_dtm >", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("update_dtm >=", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmLessThan(Date value) {
            addCriterion("update_dtm <", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmLessThanOrEqualTo(Date value) {
            addCriterion("update_dtm <=", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmIn(List<Date> values) {
            addCriterion("update_dtm in", values, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmNotIn(List<Date> values) {
            addCriterion("update_dtm not in", values, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmBetween(Date value1, Date value2) {
            addCriterion("update_dtm between", value1, value2, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmNotBetween(Date value1, Date value2) {
            addCriterion("update_dtm not between", value1, value2, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andDAddrIsNull() {
            addCriterion("d_addr is null");
            return (Criteria) this;
        }

        public Criteria andDAddrIsNotNull() {
            addCriterion("d_addr is not null");
            return (Criteria) this;
        }

        public Criteria andDAddrLike(Integer value) {
            addCriterion("d_addr like ", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrNotEqualTo(Integer value) {
            addCriterion("d_addr <>", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrGreaterThan(Integer value) {
            addCriterion("d_addr >", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrGreaterThanOrEqualTo(Integer value) {
            addCriterion("d_addr >=", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrLessThan(Integer value) {
            addCriterion("d_addr <", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrLessThanOrEqualTo(Integer value) {
            addCriterion("d_addr <=", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrIn(List<Integer> values) {
            addCriterion("d_addr in", values, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrNotIn(List<Integer> values) {
            addCriterion("d_addr not in", values, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrBetween(Integer value1, Integer value2) {
            addCriterion("d_addr between", value1, value2, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrNotBetween(Integer value1, Integer value2) {
            addCriterion("d_addr not between", value1, value2, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDTypeIsNull() {
            addCriterion("d_type is null");
            return (Criteria) this;
        }

        public Criteria andDTypeIsNotNull() {
            addCriterion("d_type is not null");
            return (Criteria) this;
        }

        public Criteria andDTypeEqualTo(Integer value) {
            addCriterion("d_type =", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeNotEqualTo(Integer value) {
            addCriterion("d_type <>", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeGreaterThan(Integer value) {
            addCriterion("d_type >", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("d_type >=", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeLessThan(Integer value) {
            addCriterion("d_type <", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeLessThanOrEqualTo(Integer value) {
            addCriterion("d_type <=", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeIn(List<Integer> values) {
            addCriterion("d_type in", values, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeNotIn(List<Integer> values) {
            addCriterion("d_type not in", values, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeBetween(Integer value1, Integer value2) {
            addCriterion("d_type between", value1, value2, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("d_type not between", value1, value2, "dType");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeIsNull() {
            addCriterion("dev_conf_code is null");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeIsNotNull() {
            addCriterion("dev_conf_code is not null");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeEqualTo(String value) {
            addCriterion("dev_conf_code =", value, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeNotEqualTo(String value) {
            addCriterion("dev_conf_code <>", value, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeGreaterThan(String value) {
            addCriterion("dev_conf_code >", value, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dev_conf_code >=", value, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeLessThan(String value) {
            addCriterion("dev_conf_code <", value, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeLessThanOrEqualTo(String value) {
            addCriterion("dev_conf_code <=", value, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeLike(String value) {
            addCriterion("dev_conf_code like", value, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeNotLike(String value) {
            addCriterion("dev_conf_code not like", value, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeIn(List<String> values) {
            addCriterion("dev_conf_code in", values, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeNotIn(List<String> values) {
            addCriterion("dev_conf_code not in", values, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeBetween(String value1, String value2) {
            addCriterion("dev_conf_code between", value1, value2, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andDevConfCodeNotBetween(String value1, String value2) {
            addCriterion("dev_conf_code not between", value1, value2, "devConfCode");
            return (Criteria) this;
        }

        public Criteria andKwIsNull() {
            addCriterion("kw is null");
            return (Criteria) this;
        }

        public Criteria andKwIsNotNull() {
            addCriterion("kw is not null");
            return (Criteria) this;
        }

        public Criteria andKwEqualTo(BigDecimal value) {
            addCriterion("kw =", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwNotEqualTo(BigDecimal value) {
            addCriterion("kw <>", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwGreaterThan(BigDecimal value) {
            addCriterion("kw >", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kw >=", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwLessThan(BigDecimal value) {
            addCriterion("kw <", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kw <=", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwIn(List<BigDecimal> values) {
            addCriterion("kw in", values, "kw");
            return (Criteria) this;
        }

        public Criteria andKwNotIn(List<BigDecimal> values) {
            addCriterion("kw not in", values, "kw");
            return (Criteria) this;
        }

        public Criteria andKwBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kw between", value1, value2, "kw");
            return (Criteria) this;
        }

        public Criteria andKwNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kw not between", value1, value2, "kw");
            return (Criteria) this;
        }

        public Criteria andKwhIsNull() {
            addCriterion("kwh is null");
            return (Criteria) this;
        }

        public Criteria andKwhIsNotNull() {
            addCriterion("kwh is not null");
            return (Criteria) this;
        }

        public Criteria andKwhEqualTo(BigDecimal value) {
            addCriterion("kwh =", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhNotEqualTo(BigDecimal value) {
            addCriterion("kwh <>", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhGreaterThan(BigDecimal value) {
            addCriterion("kwh >", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh >=", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhLessThan(BigDecimal value) {
            addCriterion("kwh <", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh <=", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhIn(List<BigDecimal> values) {
            addCriterion("kwh in", values, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhNotIn(List<BigDecimal> values) {
            addCriterion("kwh not in", values, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh between", value1, value2, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh not between", value1, value2, "kwh");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIsNull() {
            addCriterion("record_time is null");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIsNotNull() {
            addCriterion("record_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecordTimeEqualTo(String value) {
            addCriterion("record_time =", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotEqualTo(String value) {
            addCriterion("record_time <>", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeGreaterThan(String value) {
            addCriterion("record_time >", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeGreaterThanOrEqualTo(String value) {
            addCriterion("record_time >=", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLessThan(String value) {
            addCriterion("record_time <", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLessThanOrEqualTo(String value) {
            addCriterion("record_time <=", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLike(String value) {
            addCriterion("record_time like", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotLike(String value) {
            addCriterion("record_time not like", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIn(List<String> values) {
            addCriterion("record_time in", values, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotIn(List<String> values) {
            addCriterion("record_time not in", values, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeBetween(String value1, String value2) {
            addCriterion("record_time between", value1, value2, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotBetween(String value1, String value2) {
            addCriterion("record_time not between", value1, value2, "recordTime");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeIsNull() {
            addCriterion("ammeter_code is null");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeIsNotNull() {
            addCriterion("ammeter_code is not null");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeEqualTo(String value) {
            addCriterion("ammeter_code =", value, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeNotEqualTo(String value) {
            addCriterion("ammeter_code <>", value, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeGreaterThan(String value) {
            addCriterion("ammeter_code >", value, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ammeter_code >=", value, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeLessThan(String value) {
            addCriterion("ammeter_code <", value, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeLessThanOrEqualTo(String value) {
            addCriterion("ammeter_code <=", value, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeLike(String value) {
            addCriterion("ammeter_code like", value, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeNotLike(String value) {
            addCriterion("ammeter_code not like", value, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeIn(List<String> values) {
            addCriterion("ammeter_code in", values, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeNotIn(List<String> values) {
            addCriterion("ammeter_code not in", values, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeBetween(String value1, String value2) {
            addCriterion("ammeter_code between", value1, value2, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterCodeNotBetween(String value1, String value2) {
            addCriterion("ammeter_code not between", value1, value2, "ammeterCode");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andWAddrIsNull() {
            addCriterion("w_addr is null");
            return (Criteria) this;
        }

        public Criteria andWAddrIsNotNull() {
            addCriterion("w_addr is not null");
            return (Criteria) this;
        }

        public Criteria andWAddrEqualTo(Integer value) {
            addCriterion("w_addr =", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotEqualTo(Integer value) {
            addCriterion("w_addr <>", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrGreaterThan(Integer value) {
            addCriterion("w_addr >", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrGreaterThanOrEqualTo(Integer value) {
            addCriterion("w_addr >=", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrLessThan(Integer value) {
            addCriterion("w_addr <", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrLessThanOrEqualTo(Integer value) {
            addCriterion("w_addr <=", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrIn(List<Integer> values) {
            addCriterion("w_addr in", values, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotIn(List<Integer> values) {
            addCriterion("w_addr not in", values, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrBetween(Integer value1, Integer value2) {
            addCriterion("w_addr between", value1, value2, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotBetween(Integer value1, Integer value2) {
            addCriterion("w_addr not between", value1, value2, "wAddr");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}