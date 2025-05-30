// Generated from ./src/lex_par/WebbyParser.g4 by ANTLR 4.13.2
package lex_par;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link WebbyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WebbyParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link WebbyParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(WebbyParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#vars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVars(WebbyParser.VarsContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_decl(WebbyParser.Var_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#id_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_list(WebbyParser.Id_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(WebbyParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(WebbyParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#funcs_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncs_list(WebbyParser.Funcs_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#funcs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncs(WebbyParser.FuncsContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(WebbyParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(WebbyParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#return}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn(WebbyParser.ReturnContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#assign_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_stmt(WebbyParser.Assign_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(WebbyParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(WebbyParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#print_args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_args(WebbyParser.Print_argsContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#print_arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_arg(WebbyParser.Print_argContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(WebbyParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#cycle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCycle(WebbyParser.CycleContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor(WebbyParser.ForContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion(WebbyParser.ExpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(WebbyParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#termino}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermino(WebbyParser.TerminoContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(WebbyParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#cte}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCte(WebbyParser.CteContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#f_call_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_call_stmt(WebbyParser.F_call_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#f_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_call(WebbyParser.F_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebbyParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(WebbyParser.ArgsContext ctx);
}