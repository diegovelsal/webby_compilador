// Generated from ./src/lex_par/WebbyParser.g4 by ANTLR 4.13.2
package lex_par;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link WebbyParser}.
 */
public interface WebbyParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link WebbyParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(WebbyParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(WebbyParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#vars}.
	 * @param ctx the parse tree
	 */
	void enterVars(WebbyParser.VarsContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#vars}.
	 * @param ctx the parse tree
	 */
	void exitVars(WebbyParser.VarsContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_decl(WebbyParser.Var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_decl(WebbyParser.Var_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#id_list}.
	 * @param ctx the parse tree
	 */
	void enterId_list(WebbyParser.Id_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#id_list}.
	 * @param ctx the parse tree
	 */
	void exitId_list(WebbyParser.Id_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(WebbyParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(WebbyParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(WebbyParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(WebbyParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#funcs_list}.
	 * @param ctx the parse tree
	 */
	void enterFuncs_list(WebbyParser.Funcs_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#funcs_list}.
	 * @param ctx the parse tree
	 */
	void exitFuncs_list(WebbyParser.Funcs_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#funcs}.
	 * @param ctx the parse tree
	 */
	void enterFuncs(WebbyParser.FuncsContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#funcs}.
	 * @param ctx the parse tree
	 */
	void exitFuncs(WebbyParser.FuncsContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(WebbyParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(WebbyParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(WebbyParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(WebbyParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#return}.
	 * @param ctx the parse tree
	 */
	void enterReturn(WebbyParser.ReturnContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#return}.
	 * @param ctx the parse tree
	 */
	void exitReturn(WebbyParser.ReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#assign_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssign_stmt(WebbyParser.Assign_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#assign_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssign_stmt(WebbyParser.Assign_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(WebbyParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(WebbyParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(WebbyParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(WebbyParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#print_args}.
	 * @param ctx the parse tree
	 */
	void enterPrint_args(WebbyParser.Print_argsContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#print_args}.
	 * @param ctx the parse tree
	 */
	void exitPrint_args(WebbyParser.Print_argsContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#print_arg}.
	 * @param ctx the parse tree
	 */
	void enterPrint_arg(WebbyParser.Print_argContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#print_arg}.
	 * @param ctx the parse tree
	 */
	void exitPrint_arg(WebbyParser.Print_argContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(WebbyParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(WebbyParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#cycle}.
	 * @param ctx the parse tree
	 */
	void enterCycle(WebbyParser.CycleContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#cycle}.
	 * @param ctx the parse tree
	 */
	void exitCycle(WebbyParser.CycleContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#for}.
	 * @param ctx the parse tree
	 */
	void enterFor(WebbyParser.ForContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#for}.
	 * @param ctx the parse tree
	 */
	void exitFor(WebbyParser.ForContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(WebbyParser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(WebbyParser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(WebbyParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(WebbyParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#termino}.
	 * @param ctx the parse tree
	 */
	void enterTermino(WebbyParser.TerminoContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#termino}.
	 * @param ctx the parse tree
	 */
	void exitTermino(WebbyParser.TerminoContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(WebbyParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(WebbyParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#cte}.
	 * @param ctx the parse tree
	 */
	void enterCte(WebbyParser.CteContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#cte}.
	 * @param ctx the parse tree
	 */
	void exitCte(WebbyParser.CteContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#f_call_stmt}.
	 * @param ctx the parse tree
	 */
	void enterF_call_stmt(WebbyParser.F_call_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#f_call_stmt}.
	 * @param ctx the parse tree
	 */
	void exitF_call_stmt(WebbyParser.F_call_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#f_call}.
	 * @param ctx the parse tree
	 */
	void enterF_call(WebbyParser.F_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#f_call}.
	 * @param ctx the parse tree
	 */
	void exitF_call(WebbyParser.F_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebbyParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(WebbyParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebbyParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(WebbyParser.ArgsContext ctx);
}