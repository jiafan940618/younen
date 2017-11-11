package com.yn.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PzwExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PzwExample() {
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

        public Criteria andKwhTotalIsNull() {
            addCriterion("kwh_total is null");
            return (Criteria) this;
        }

        public Criteria andKwhTotalIsNotNull() {
            addCriterion("kwh_total is not null");
            return (Criteria) this;
        }

        public Criteria andKwhTotalEqualTo(BigDecimal value) {
            addCriterion("kwh_total =", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalNotEqualTo(BigDecimal value) {
            addCriterion("kwh_total <>", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalGreaterThan(BigDecimal value) {
            addCriterion("kwh_total >", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh_total >=", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalLessThan(BigDecimal value) {
            addCriterion("kwh_total <", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh_total <=", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalIn(List<BigDecimal> values) {
            addCriterion("kwh_total in", values, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalNotIn(List<BigDecimal> values) {
            addCriterion("kwh_total not in", values, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh_total between", value1, value2, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh_total not between", value1, value2, "kwhTotal");
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

        public Criteria andIsOkIsNull() {
            addCriterion("is_ok is null");
            return (Criteria) this;
        }

        public Criteria andIsOkIsNotNull() {
            addCriterion("is_ok is not null");
            return (Criteria) this;
        }

        public Criteria andIsOkEqualTo(Integer value) {
            addCriterion("is_ok =", value, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkNotEqualTo(Integer value) {
            addCriterion("is_ok <>", value, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkGreaterThan(Integer value) {
            addCriterion("is_ok >", value, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_ok >=", value, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkLessThan(Integer value) {
            addCriterion("is_ok <", value, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkLessThanOrEqualTo(Integer value) {
            addCriterion("is_ok <=", value, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkIn(List<Integer> values) {
            addCriterion("is_ok in", values, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkNotIn(List<Integer> values) {
            addCriterion("is_ok not in", values, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkBetween(Integer value1, Integer value2) {
            addCriterion("is_ok between", value1, value2, "isOk");
            return (Criteria) this;
        }

        public Criteria andIsOkNotBetween(Integer value1, Integer value2) {
            addCriterion("is_ok not between", value1, value2, "isOk");
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

        public Criteria andMonthMaxKwhIsNull() {
            addCriterion("month_max_kwh is null");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhIsNotNull() {
            addCriterion("month_max_kwh is not null");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhEqualTo(BigDecimal value) {
            addCriterion("month_max_kwh =", value, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhNotEqualTo(BigDecimal value) {
            addCriterion("month_max_kwh <>", value, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhGreaterThan(BigDecimal value) {
            addCriterion("month_max_kwh >", value, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("month_max_kwh >=", value, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhLessThan(BigDecimal value) {
            addCriterion("month_max_kwh <", value, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhLessThanOrEqualTo(BigDecimal value) {
            addCriterion("month_max_kwh <=", value, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhIn(List<BigDecimal> values) {
            addCriterion("month_max_kwh in", values, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhNotIn(List<BigDecimal> values) {
            addCriterion("month_max_kwh not in", values, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("month_max_kwh between", value1, value2, "monthMaxKwh");
            return (Criteria) this;
        }

        public Criteria andMonthMaxKwhNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("month_max_kwh not between", value1, value2, "monthMaxKwh");
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