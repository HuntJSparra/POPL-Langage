import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
    The default is visitChildren(CONTEXT). Are we supposed to define the behavior in the context itself?
*/

public class Main {
    static class Union {
        Class currentType;
        Boolean booleanType;
        Integer numberType;

        public Union(Boolean bol) {
            currentType = Boolean.class;
            booleanType = bol;
        }

        public Union(Integer i) {
            currentType = Integer.class;
            numberType = i;
        }

        @Override
        public String toString() {
            if (currentType.equals(Boolean.class)) {
                return booleanType.toString();
            } else if (currentType.equals(Integer.class)) {
                return numberType.toString();
            } else {
                throw new IllegalStateException();
            }
        }

        public void setType(Object obj) {
            Class objClass = obj.getClass();
            if (objClass.equals(Boolean.class)) {
                booleanType = (Boolean)obj;
            } else if (objClass.equals(Integer.class)) {
                numberType = (Integer)obj;
            } else {
                throw new IllegalArgumentException();
            }

            currentType = objClass;
        }

        public Object getValue() {
            if (currentType.equals(Boolean.class)) {
                return booleanType;
            }
            if (currentType.equals(Integer.class)) {
                return numberType;
            } else {
                throw new IllegalStateException();
            }
        }

        public Class getType() {
            return currentType;
        }

        /* "Overriding" basic operations */
        // BOOLEAN
        public Union not() {
            if (!currentType.equals(Boolean.class)) {
                throw new IllegalStateException();
            }

            return new Union(!booleanType);
        }

        public Union and(Boolean other) {
            if (!currentType.equals(Boolean.class)) {
                throw new IllegalStateException();
            }

            return new Union(booleanType && other);
        }

        public Union and(Union other) {
            if (!other.getType().equals(Boolean.class)) {
                throw new IllegalArgumentException();
            }

            Boolean otherValue = (Boolean)other.getValue();

            return and(otherValue);
        }

        public Union or(Boolean other) {
            if (!currentType.equals(Boolean.class)) {
                throw new IllegalStateException();
            }

            return new Union(booleanType || other);
        }

        public Union or(Union other) {
            if (!other.getType().equals(Boolean.class)) {
                throw new IllegalArgumentException();
            }

            Boolean otherValue = (Boolean)other.getValue();

            return or(otherValue);
        }

        // INTEGER
        public Union negative() {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(-numberType);
        }

        public Union add(Integer other) {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(numberType + other);
        }

        public Union add(Union other) {
            if (!other.getType().equals(Integer.class)) {
                throw new IllegalArgumentException();
            }

            Integer otherValue = (Integer)other.getValue();

            return add(otherValue);
        }

        public Union sub(Integer other) {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(numberType - other);
        }

        public Union sub(Union other) {
            if (!other.getType().equals(Integer.class)) {
                throw new IllegalArgumentException();
            }

            Integer otherValue = (Integer)other.getValue();

            return sub(otherValue);
        }

        public Union multiply(Integer other) {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(numberType * other);
        }

        public Union multiply(Union other) {
            if (!other.getType().equals(Integer.class)) {
                throw new IllegalArgumentException();
            }

            Integer otherValue = (Integer)other.getValue();

            return multiply(otherValue);
        }

        public Union divide(Integer other) {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(numberType / other);
        }

        public Union divide(Union other) {
            if (!other.getType().equals(Integer.class)) {
                throw new IllegalArgumentException();
            }

            Integer otherValue = (Integer)other.getValue();

            return divide(otherValue);
        }

        public Union greaterThan(Integer other) {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(numberType > other);
        }

        public Union greaterThan(Union other) {
            if (!other.getType().equals(Integer.class)) {
                throw new IllegalArgumentException();
            }

            Integer otherValue = (Integer)other.getValue();

            return greaterThan(otherValue);
        }

        public Union lessThan(Integer other) {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(numberType < other);
        }

        public Union lessThan(Union other) {
            if (!other.getType().equals(Integer.class)) {
                throw new IllegalArgumentException();
            }

            Integer otherValue = (Integer)other.getValue();

            return lessThan(otherValue);
        }

        public Union greaterThanEquals(Integer other) {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(numberType >= other);
        }

        public Union greaterThanEquals(Union other) {
            if (!other.getType().equals(Integer.class)) {
                throw new IllegalArgumentException();
            }

            Integer otherValue = (Integer)other.getValue();

            return greaterThanEquals(otherValue);
        }

        public Union lessThanEquals(Integer other) {
            if (!currentType.equals(Integer.class)) {
                throw new IllegalStateException();
            }

            return new Union(numberType <= other);
        }

        public Union lessThanEquals(Union other) {
            if (!other.getType().equals(Integer.class)) {
                throw new IllegalArgumentException();
            }

            Integer otherValue = (Integer)other.getValue();

            return lessThanEquals(otherValue);
        }

        // MIXED
        public Union isEqual(Object other) {
            if (currentType.equals(Boolean.class)) {
                if (other instanceof Boolean) {
                    return new Union(booleanType.equals(other));
                } else if (other instanceof Union) {
                    if (((Union)other).getType().equals(Boolean.class)) {
                        return new Union(booleanType.equals((Boolean)((Union)other).getValue()));
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            } else if (currentType.equals(Integer.class)) {
                if (other instanceof Integer) {
                    return new Union(numberType.equals((Integer)((Union)other).getValue()));
                } else if (other instanceof Union) {
                    if (((Union)other).getType().equals(Integer.class)) {
                        return new Union(numberType.equals((Integer)((Union)other).getValue()));
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalStateException();
            }
        }

        public Union isNotEqual(Object other) {
            if (currentType.equals(Boolean.class)) {
                if (other instanceof Boolean) {
                    return new Union(booleanType.equals(other));
                } else if (other instanceof Union) {
                    if (((Union)other).getType().equals(Boolean.class)) {
                        return new Union(!booleanType.equals((Boolean)((Union)other).getValue()));
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            } else if (currentType.equals(Integer.class)) {
                if (other instanceof Integer) {
                    return new Union(numberType.equals((Integer)((Union)other).getValue()));
                } else if (other instanceof Union) {
                    if (((Union)other).getType().equals(Integer.class)) {
                        return new Union(!numberType.equals((Integer)((Union)other).getValue()));
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    static class Function {
        public ArrayList<String> variableNames;
        public boollangParser.ExpContext expContext;

        Function(ArrayList<String> vars, boollangParser.ExpContext exp) {
            variableNames = vars;
            expContext = exp;
        }
    }

    static class BLSentenceVisitor extends boollangBaseVisitor<Union> {
        HashMap<String, Union> valueTable;
        HashMap<String, Function> functionTable;

        BLSentenceVisitor() {
            valueTable = new HashMap<String, Union>();
            functionTable = new HashMap<String, Function>();
        }

        @Override
        public Union visitSentence(boollangParser.SentenceContext ctx) {
            return this.visit(ctx.exp());
        }

        @Override
        public Union visitExp(boollangParser.ExpContext ctx) {
            if (ctx.opp() != null) {
                return this.visit(ctx.opp());
            } else if (ctx.op() != null) {
                return this.visit(ctx.op());
            } else if (ctx.val() != null) {
                return this.visit(ctx.val());
            } else if (ctx.var() != null) {
                return this.visit(ctx.var());
            } else {
                throw new UnsupportedOperationException();
            }
        }


        /* Value Processing */
        
        @Override
        public Union visitVal(boollangParser.ValContext ctx) {
            if (ctx.number() != null) {
                return this.visit(ctx.number());
            } else {
                return this.visit(ctx.bool());
            }
        }

        @Override
        public Union visitNumber(boollangParser.NumberContext ctx) {
            return new Union(Integer.parseInt(ctx.getText()));
        }

        @Override
        public Union visitBool(boollangParser.BoolContext ctx) {
            return new Union(ctx.TRUE() != null);
        }


        /* Variable Processing */

        @Override
        public Union visitVar(boollangParser.VarContext ctx) {
            Union value = valueTable.get(ctx.getText());
            if (value == null) {
                // throw new NullPointerException();
            }
            return value;
        }
        
        @Override
        public Union visitAnds(boollangParser.AndsContext ctx) {
            return this.visit(ctx.exp(0)).and(this.visit(ctx.exp(1)));
        }

        @Override
        public Union visitOrs(boollangParser.OrsContext ctx) {
            return this.visit(ctx.exp(0)).or(this.visit(ctx.exp(1)));
        }
        
        @Override
        public Union visitNots(boollangParser.NotsContext ctx) {
            return this.visit(ctx.exp()).not();
        }

        /* Visit Operation */
        
        @Override
        public Union visitOp(boollangParser.OpContext ctx) {
            if (ctx.nots() != null) {
                return this.visit(ctx.nots());
            } else if (ctx.ands() != null) {
                return this.visit(ctx.ands());
            } else if (ctx.ors() != null) {
                return this.visit(ctx.ors());
            } else if (ctx.adds() != null) {
                return this.visit(ctx.adds());
            } else if (ctx.subs() != null) {
                return this.visit(ctx.subs());
            } else if (ctx.negs() != null) {
                return this.visit(ctx.negs());
            } else if (ctx.mults() != null) {
                return this.visit(ctx.mults());
            } else if (ctx.divs() != null) {
                return this.visit(ctx.divs());
            } else if (ctx.gts() != null) {
                return this.visit(ctx.gts());
            } else if (ctx.gtes() != null) {
                return this.visit(ctx.gtes());
            } else if (ctx.lts() != null) {
                return this.visit(ctx.lts());
            } else if (ctx.ltes() != null) {
                return this.visit(ctx.ltes());
            } else if (ctx.eqs() != null) {
                return this.visit(ctx.eqs());
            } else if (ctx.neqs() != null) {
                return this.visit(ctx.neqs());
            } else if (ctx.ifs() != null) {
                return this.visit(ctx.ifs());
            } else if (ctx.lets() != null) {
                return this.visit(ctx.lets());
            } else if (ctx.lambdas() != null) {
                return this.visit(ctx.lambdas());
            } else if (ctx.funcs() != null) {
                return this.visit(ctx.funcs());
            } else {
                throw new UnsupportedOperationException(ctx.getChild(0).getText());
            }
        }
        
        @Override
        public Union visitOpp(boollangParser.OppContext ctx) {
            return this.visit(ctx.exp());
        }

        /* Number Operations */
        @Override
        public Union visitAdds(boollangParser.AddsContext ctx) {
            return this.visit(ctx.exp(0)).add(this.visit(ctx.exp(1)));
        }

        @Override
        public Union visitSubs(boollangParser.SubsContext ctx) {
            return this.visit(ctx.exp(0)).sub(this.visit(ctx.exp(1)));
        }

        public Union visitNegs(boollangParser.NegsContext ctx) {
            return this.visit(ctx.exp()).negative();
        }

        public Union visitMults(boollangParser.MultsContext ctx) {
            return this.visit(ctx.exp(0)).multiply(this.visit(ctx.exp(1)));
        }

        public Union visitDivs(boollangParser.DivsContext ctx) {
            return this.visit(ctx.exp(0)).divide(this.visit(ctx.exp(1)));
        }

        public Union visitGts(boollangParser.GtsContext ctx) {
            return this.visit(ctx.exp(0)).greaterThan(this.visit(ctx.exp(1)));
        }

        public Union visitGtes(boollangParser.GtesContext ctx) {
            return this.visit(ctx.exp(0)).greaterThanEquals(this.visit(ctx.exp(1)));
        }

        public Union visitLts(boollangParser.LtsContext ctx) {
            return this.visit(ctx.exp(0)).lessThan(this.visit(ctx.exp(1)));
        }

        public Union visitLtes(boollangParser.LtesContext ctx) {
            return this.visit(ctx.exp(0)).lessThanEquals(this.visit(ctx.exp(1)));
        }


        /* Mixed */

        @Override
        public Union visitEqs(boollangParser.EqsContext ctx) {
            return this.visit(ctx.exp(0)).isEqual(this.visit(ctx.exp(1)));
        }

        @Override
        public Union visitNeqs(boollangParser.NeqsContext ctx) {
            return this.visit(ctx.exp(0)).isNotEqual(this.visit(ctx.exp(1)));
        }

        @Override
        public Union visitIfs(boollangParser.IfsContext ctx) {
            Union booleanExpression = this.visit(ctx.exp(0));

            if (booleanExpression.getType() != Boolean.class) {
                throw new IllegalArgumentException();
            }

            if ((Boolean)booleanExpression.getValue()) {
                return this.visit(ctx.exp(1));
            } else {
                return this.visit(ctx.exp(2));
            }
        }

        @Override
        public Union visitLets(boollangParser.LetsContext ctx) {
            String variable = ctx.var().getText();
            Union oldValue = this.visit(ctx.var());
            Union newValue = this.visit(ctx.exp(0));

            valueTable.put(variable, newValue);

            Union returnValue = this.visit(ctx.exp(1));

            // Cleanup
            if (oldValue != null) {
                valueTable.put(variable, oldValue);
            } else {
                valueTable.remove(variable);
            }

            return returnValue;
        }

        /* Function */
        @Override
        public Union visitLambdas(boollangParser.LambdasContext ctx) {
            String functionName = ctx.var(0).getText();
            boollangParser.ExpContext exp = ctx.exp(0);
            ArrayList<String> varNames = new ArrayList<String>();
            for (int i=1; i<ctx.getChildCount()-3; i++) { // Last 2 vars are exps and the first is the function name, so there there are size-3 variable names
                varNames.add(ctx.var(i).getText());
            }

            Function oldFunction = functionTable.get(functionName);
            Function newFunction = new Function(varNames, exp);
            functionTable.put(functionName, newFunction);

            Union returnValue = this.visit(ctx.exp(1));

            if (oldFunction != null) {
                functionTable.put(functionName, oldFunction);
            } else {
                functionTable.remove(functionName);
            }

            return returnValue;
        }

        @Override
        public Union visitFuncs(boollangParser.FuncsContext ctx) {
            String functionName = ctx.var().getText();
            Function func = functionTable.get(functionName);
            ArrayList<Union> parameters = new ArrayList<Union>();
            ArrayList<Union> oldParameters = new ArrayList<Union>();

            for (int i=0; i<ctx.getChildCount()-2; i++) { // 1st var is the function name, and DO is a child
                Union parameter = this.visit(ctx.exp(i));
                parameters.add(parameter);
            }

            if (func == null) {
                throw new IllegalArgumentException("Function "+functionName+" not found");
            } else if (parameters.size() != func.variableNames.size()) {
                throw new IllegalArgumentException("Expected: "+func.variableNames.size()+", Got: "+parameters.size());
            }

            for (int i=0; i<parameters.size(); i++) {
                oldParameters.add(valueTable.get(func.variableNames.get(i)));
                valueTable.put(func.variableNames.get(i), parameters.get(i));
            }

            Union returnValue = this.visit(func.expContext);

            for (int i=0; i<parameters.size(); i++) {
                Union oldParameter = oldParameters.get(i);

                if (oldParameter != null) {
                    valueTable.put(func.variableNames.get(i), oldParameter);
                } else {
                    valueTable.remove(func.variableNames.get(i));
                }
            }

            return returnValue;
        }
    }

    public static void main(String[] args) throws IOException {
        // boollangLexer lexer = new boollangLexer(CharStreams.fromString("LET statement (OR TRUE FALSE) (IF statement ? (LET X 2 (MULT 5 X)) : (LET X -2 (MULT 5 X)))\n"));
        // boollangLexer lexer = new boollangLexer(CharStreams.fromString("LAMBDA myFunc x y (DIV x y) (CALL myFunc 25 5)\n"));
        // boollangLexer lexer = new boollangLexer(CharStreams.fromString("LET x 5 LET y 4 SUB x y\n"));
        // boollangLexer lexer = new boollangLexer(CharStreams.fromString("LET num (5) (num)\n"));
        // boollangLexer lexer = new boollangLexer(CharStreams.fromString("DIV 500 100\n"));
        //boollangLexer lexer = new boollangLexer(CharStreams.fromString("LAMBDA fib n (IF (LTE n 1) ? 1 : (ADD (CALL fib (SUB n 1)) (CALL fib (SUB n 2)))) (CALL fib 5)\n"));
        
        if (args.length == 0) {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter code, ending in a newline:");
            while (s.hasNextLine()) {
                boollangLexer lexer = new boollangLexer(CharStreams.fromString(s.nextLine()));
                boollangParser parser = new boollangParser(new CommonTokenStream(lexer));
                boollangParser.SentenceContext tree = parser.sentence();
                Union result = new BLSentenceVisitor().visit(tree);
                System.out.println("Result: "+result.toString());
            }
        } else {
            boollangLexer lexer = new boollangLexer(CharStreams.fromFileName(args[0]+".lang"));
            boollangParser parser = new boollangParser(new CommonTokenStream(lexer));
            boollangParser.SentenceContext tree = parser.sentence();
            Union result = new BLSentenceVisitor().visit(tree);
            System.out.println("Result: "+result.toString());
        }
    }
}
