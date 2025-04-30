// Generated from WebbyParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class WebbyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PROGRAM=1, MAIN=2, END=3, VAR=4, PRINT=5, IF=6, ELSE=7, WHILE=8, DO=9, 
		VOID=10, INT=11, FLOAT=12, ID=13, CTE_INT=14, CTE_FLOAT=15, CTE_STRING=16, 
		ADD=17, SUB=18, MUL=19, DIV=20, ASSIGN=21, EQUAL=22, NOTEQUAL=23, LESS=24, 
		GREATER=25, LPAREN=26, RPAREN=27, LBRACE=28, RBRACE=29, LBRACK=30, RBRACK=31, 
		SEMICOLON=32, COLON=33, COMMA=34, WS=35;
	public static final int
		RULE_programa = 0, RULE_vars = 1, RULE_id_list = 2, RULE_tipo = 3, RULE_funcs = 4, 
		RULE_body = 5, RULE_statement = 6, RULE_assign = 7, RULE_print_stmt = 8, 
		RULE_condition = 9, RULE_cycle = 10, RULE_expresion = 11, RULE_exp = 12, 
		RULE_termino = 13, RULE_factor = 14, RULE_cte = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"programa", "vars", "id_list", "tipo", "funcs", "body", "statement", 
			"assign", "print_stmt", "condition", "cycle", "expresion", "exp", "termino", 
			"factor", "cte"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'program'", "'main'", "'end'", "'var'", "'print'", "'if'", "'else'", 
			"'while'", "'do'", "'void'", "'int'", "'float'", null, null, null, null, 
			"'+'", "'-'", "'*'", "'/'", "'='", "'=='", "'!='", "'<'", "'>'", "'('", 
			"')'", "'{'", "'}'", "'['", "']'", "';'", "':'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PROGRAM", "MAIN", "END", "VAR", "PRINT", "IF", "ELSE", "WHILE", 
			"DO", "VOID", "INT", "FLOAT", "ID", "CTE_INT", "CTE_FLOAT", "CTE_STRING", 
			"ADD", "SUB", "MUL", "DIV", "ASSIGN", "EQUAL", "NOTEQUAL", "LESS", "GREATER", 
			"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMICOLON", 
			"COLON", "COMMA", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "WebbyParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public WebbyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramaContext extends ParserRuleContext {
		public TerminalNode PROGRAM() { return getToken(WebbyParser.PROGRAM, 0); }
		public TerminalNode ID() { return getToken(WebbyParser.ID, 0); }
		public TerminalNode SEMICOLON() { return getToken(WebbyParser.SEMICOLON, 0); }
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public FuncsContext funcs() {
			return getRuleContext(FuncsContext.class,0);
		}
		public TerminalNode MAIN() { return getToken(WebbyParser.MAIN, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public TerminalNode END() { return getToken(WebbyParser.END, 0); }
		public ProgramaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programa; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterPrograma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitPrograma(this);
		}
	}

	public final ProgramaContext programa() throws RecognitionException {
		ProgramaContext _localctx = new ProgramaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_programa);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(PROGRAM);
			setState(33);
			match(ID);
			setState(34);
			match(SEMICOLON);
			setState(35);
			vars();
			setState(36);
			funcs();
			setState(37);
			match(MAIN);
			setState(38);
			body();
			setState(39);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarsContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(WebbyParser.VAR, 0); }
		public TerminalNode COLON() { return getToken(WebbyParser.COLON, 0); }
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public Id_listContext id_list() {
			return getRuleContext(Id_listContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(WebbyParser.SEMICOLON, 0); }
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public VarsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vars; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterVars(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitVars(this);
		}
	}

	public final VarsContext vars() throws RecognitionException {
		VarsContext _localctx = new VarsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_vars);
		try {
			setState(49);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(41);
				match(VAR);
				setState(42);
				match(COLON);
				setState(43);
				tipo();
				setState(44);
				id_list();
				setState(45);
				match(SEMICOLON);
				setState(46);
				vars();
				}
				break;
			case MAIN:
			case VOID:
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Id_listContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(WebbyParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(WebbyParser.ID, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(WebbyParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(WebbyParser.COMMA, i);
		}
		public Id_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterId_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitId_list(this);
		}
	}

	public final Id_listContext id_list() throws RecognitionException {
		Id_listContext _localctx = new Id_listContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_id_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(ID);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(52);
				match(COMMA);
				setState(53);
				match(ID);
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TipoContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(WebbyParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(WebbyParser.FLOAT, 0); }
		public TipoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tipo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterTipo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitTipo(this);
		}
	}

	public final TipoContext tipo() throws RecognitionException {
		TipoContext _localctx = new TipoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tipo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==FLOAT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncsContext extends ParserRuleContext {
		public TerminalNode VOID() { return getToken(WebbyParser.VOID, 0); }
		public List<TerminalNode> ID() { return getTokens(WebbyParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(WebbyParser.ID, i);
		}
		public TerminalNode LPAREN() { return getToken(WebbyParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(WebbyParser.RPAREN, 0); }
		public TerminalNode COLON() { return getToken(WebbyParser.COLON, 0); }
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public FuncsContext funcs() {
			return getRuleContext(FuncsContext.class,0);
		}
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public FuncsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterFuncs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitFuncs(this);
		}
	}

	public final FuncsContext funcs() throws RecognitionException {
		FuncsContext _localctx = new FuncsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_funcs);
		int _la;
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				match(VOID);
				setState(62);
				match(ID);
				setState(63);
				match(LPAREN);
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INT || _la==FLOAT) {
					{
					setState(64);
					tipo();
					}
				}

				setState(67);
				match(ID);
				setState(68);
				match(RPAREN);
				setState(69);
				match(COLON);
				setState(70);
				vars();
				setState(71);
				body();
				setState(72);
				funcs();
				}
				break;
			case MAIN:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(WebbyParser.LBRACE, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(WebbyParser.RBRACE, 0); }
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitBody(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_body);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(LBRACE);
			setState(78);
			statement();
			setState(79);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public CycleContext cycle() {
			return getRuleContext(CycleContext.class,0);
		}
		public Print_stmtContext print_stmt() {
			return getRuleContext(Print_stmtContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		try {
			setState(85);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(81);
				assign();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(82);
				condition();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(83);
				cycle();
				}
				break;
			case PRINT:
				enterOuterAlt(_localctx, 4);
				{
				setState(84);
				print_stmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(WebbyParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(WebbyParser.ASSIGN, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(WebbyParser.SEMICOLON, 0); }
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitAssign(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(ID);
			setState(88);
			match(ASSIGN);
			setState(89);
			expresion();
			setState(90);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Print_stmtContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(WebbyParser.PRINT, 0); }
		public TerminalNode LPAREN() { return getToken(WebbyParser.LPAREN, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WebbyParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(WebbyParser.SEMICOLON, 0); }
		public Print_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterPrint_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitPrint_stmt(this);
		}
	}

	public final Print_stmtContext print_stmt() throws RecognitionException {
		Print_stmtContext _localctx = new Print_stmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_print_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(PRINT);
			setState(93);
			match(LPAREN);
			setState(94);
			expresion();
			setState(95);
			match(RPAREN);
			setState(96);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(WebbyParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(WebbyParser.LPAREN, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WebbyParser.RPAREN, 0); }
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(WebbyParser.ELSE, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitCondition(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(IF);
			setState(99);
			match(LPAREN);
			setState(100);
			expresion();
			setState(101);
			match(RPAREN);
			setState(102);
			body();
			setState(103);
			match(ELSE);
			setState(104);
			body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CycleContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(WebbyParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(WebbyParser.LPAREN, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WebbyParser.RPAREN, 0); }
		public TerminalNode DO() { return getToken(WebbyParser.DO, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public CycleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cycle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterCycle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitCycle(this);
		}
	}

	public final CycleContext cycle() throws RecognitionException {
		CycleContext _localctx = new CycleContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_cycle);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(WHILE);
			setState(107);
			match(LPAREN);
			setState(108);
			expresion();
			setState(109);
			match(RPAREN);
			setState(110);
			match(DO);
			setState(111);
			body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpresionContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode LESS() { return getToken(WebbyParser.LESS, 0); }
		public TerminalNode GREATER() { return getToken(WebbyParser.GREATER, 0); }
		public TerminalNode EQUAL() { return getToken(WebbyParser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(WebbyParser.NOTEQUAL, 0); }
		public ExpresionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expresion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterExpresion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitExpresion(this);
		}
	}

	public final ExpresionContext expresion() throws RecognitionException {
		ExpresionContext _localctx = new ExpresionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expresion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			exp();
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 62914560L) != 0)) {
				{
				setState(114);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 62914560L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(115);
				exp();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpContext extends ParserRuleContext {
		public List<TerminoContext> termino() {
			return getRuleContexts(TerminoContext.class);
		}
		public TerminoContext termino(int i) {
			return getRuleContext(TerminoContext.class,i);
		}
		public List<TerminalNode> ADD() { return getTokens(WebbyParser.ADD); }
		public TerminalNode ADD(int i) {
			return getToken(WebbyParser.ADD, i);
		}
		public List<TerminalNode> SUB() { return getTokens(WebbyParser.SUB); }
		public TerminalNode SUB(int i) {
			return getToken(WebbyParser.SUB, i);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitExp(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_exp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			termino();
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ADD || _la==SUB) {
				{
				{
				setState(119);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(120);
				termino();
				}
				}
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TerminoContext extends ParserRuleContext {
		public List<FactorContext> factor() {
			return getRuleContexts(FactorContext.class);
		}
		public FactorContext factor(int i) {
			return getRuleContext(FactorContext.class,i);
		}
		public List<TerminalNode> MUL() { return getTokens(WebbyParser.MUL); }
		public TerminalNode MUL(int i) {
			return getToken(WebbyParser.MUL, i);
		}
		public List<TerminalNode> DIV() { return getTokens(WebbyParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(WebbyParser.DIV, i);
		}
		public TerminoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termino; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterTermino(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitTermino(this);
		}
	}

	public final TerminoContext termino() throws RecognitionException {
		TerminoContext _localctx = new TerminoContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_termino);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			factor();
			setState(131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MUL || _la==DIV) {
				{
				{
				setState(127);
				_la = _input.LA(1);
				if ( !(_la==MUL || _la==DIV) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(128);
				factor();
				}
				}
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(WebbyParser.ID, 0); }
		public CteContext cte() {
			return getRuleContext(CteContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(WebbyParser.LPAREN, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WebbyParser.RPAREN, 0); }
		public TerminalNode ADD() { return getToken(WebbyParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(WebbyParser.SUB, 0); }
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitFactor(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_factor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ADD || _la==SUB) {
				{
				setState(134);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(143);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(137);
				match(ID);
				}
				break;
			case CTE_INT:
			case CTE_FLOAT:
			case CTE_STRING:
				{
				setState(138);
				cte();
				}
				break;
			case LPAREN:
				{
				setState(139);
				match(LPAREN);
				setState(140);
				expresion();
				setState(141);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CteContext extends ParserRuleContext {
		public TerminalNode CTE_INT() { return getToken(WebbyParser.CTE_INT, 0); }
		public TerminalNode CTE_FLOAT() { return getToken(WebbyParser.CTE_FLOAT, 0); }
		public TerminalNode CTE_STRING() { return getToken(WebbyParser.CTE_STRING, 0); }
		public CteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cte; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).enterCte(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebbyParserListener ) ((WebbyParserListener)listener).exitCte(this);
		}
	}

	public final CteContext cte() throws RecognitionException {
		CteContext _localctx = new CteContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_cte);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 114688L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001#\u0094\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001"+
		"2\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u00027\b\u0002\n\u0002"+
		"\f\u0002:\t\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0003\u0004B\b\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003"+
		"\u0004L\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006V\b\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000bu\b\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0005\fz\b\f\n\f\f\f}\t\f\u0001\r\u0001\r\u0001\r\u0005"+
		"\r\u0082\b\r\n\r\f\r\u0085\t\r\u0001\u000e\u0003\u000e\u0088\b\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003"+
		"\u000e\u0090\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0000\u0000\u0010"+
		"\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a"+
		"\u001c\u001e\u0000\u0005\u0001\u0000\u000b\f\u0001\u0000\u0016\u0019\u0001"+
		"\u0000\u0011\u0012\u0001\u0000\u0013\u0014\u0001\u0000\u000e\u0010\u0090"+
		"\u0000 \u0001\u0000\u0000\u0000\u00021\u0001\u0000\u0000\u0000\u00043"+
		"\u0001\u0000\u0000\u0000\u0006;\u0001\u0000\u0000\u0000\bK\u0001\u0000"+
		"\u0000\u0000\nM\u0001\u0000\u0000\u0000\fU\u0001\u0000\u0000\u0000\u000e"+
		"W\u0001\u0000\u0000\u0000\u0010\\\u0001\u0000\u0000\u0000\u0012b\u0001"+
		"\u0000\u0000\u0000\u0014j\u0001\u0000\u0000\u0000\u0016q\u0001\u0000\u0000"+
		"\u0000\u0018v\u0001\u0000\u0000\u0000\u001a~\u0001\u0000\u0000\u0000\u001c"+
		"\u0087\u0001\u0000\u0000\u0000\u001e\u0091\u0001\u0000\u0000\u0000 !\u0005"+
		"\u0001\u0000\u0000!\"\u0005\r\u0000\u0000\"#\u0005 \u0000\u0000#$\u0003"+
		"\u0002\u0001\u0000$%\u0003\b\u0004\u0000%&\u0005\u0002\u0000\u0000&\'"+
		"\u0003\n\u0005\u0000\'(\u0005\u0003\u0000\u0000(\u0001\u0001\u0000\u0000"+
		"\u0000)*\u0005\u0004\u0000\u0000*+\u0005!\u0000\u0000+,\u0003\u0006\u0003"+
		"\u0000,-\u0003\u0004\u0002\u0000-.\u0005 \u0000\u0000./\u0003\u0002\u0001"+
		"\u0000/2\u0001\u0000\u0000\u000002\u0001\u0000\u0000\u00001)\u0001\u0000"+
		"\u0000\u000010\u0001\u0000\u0000\u00002\u0003\u0001\u0000\u0000\u0000"+
		"38\u0005\r\u0000\u000045\u0005\"\u0000\u000057\u0005\r\u0000\u000064\u0001"+
		"\u0000\u0000\u00007:\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u0000"+
		"89\u0001\u0000\u0000\u00009\u0005\u0001\u0000\u0000\u0000:8\u0001\u0000"+
		"\u0000\u0000;<\u0007\u0000\u0000\u0000<\u0007\u0001\u0000\u0000\u0000"+
		"=>\u0005\n\u0000\u0000>?\u0005\r\u0000\u0000?A\u0005\u001a\u0000\u0000"+
		"@B\u0003\u0006\u0003\u0000A@\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000"+
		"\u0000BC\u0001\u0000\u0000\u0000CD\u0005\r\u0000\u0000DE\u0005\u001b\u0000"+
		"\u0000EF\u0005!\u0000\u0000FG\u0003\u0002\u0001\u0000GH\u0003\n\u0005"+
		"\u0000HI\u0003\b\u0004\u0000IL\u0001\u0000\u0000\u0000JL\u0001\u0000\u0000"+
		"\u0000K=\u0001\u0000\u0000\u0000KJ\u0001\u0000\u0000\u0000L\t\u0001\u0000"+
		"\u0000\u0000MN\u0005\u001c\u0000\u0000NO\u0003\f\u0006\u0000OP\u0005\u001d"+
		"\u0000\u0000P\u000b\u0001\u0000\u0000\u0000QV\u0003\u000e\u0007\u0000"+
		"RV\u0003\u0012\t\u0000SV\u0003\u0014\n\u0000TV\u0003\u0010\b\u0000UQ\u0001"+
		"\u0000\u0000\u0000UR\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000"+
		"UT\u0001\u0000\u0000\u0000V\r\u0001\u0000\u0000\u0000WX\u0005\r\u0000"+
		"\u0000XY\u0005\u0015\u0000\u0000YZ\u0003\u0016\u000b\u0000Z[\u0005 \u0000"+
		"\u0000[\u000f\u0001\u0000\u0000\u0000\\]\u0005\u0005\u0000\u0000]^\u0005"+
		"\u001a\u0000\u0000^_\u0003\u0016\u000b\u0000_`\u0005\u001b\u0000\u0000"+
		"`a\u0005 \u0000\u0000a\u0011\u0001\u0000\u0000\u0000bc\u0005\u0006\u0000"+
		"\u0000cd\u0005\u001a\u0000\u0000de\u0003\u0016\u000b\u0000ef\u0005\u001b"+
		"\u0000\u0000fg\u0003\n\u0005\u0000gh\u0005\u0007\u0000\u0000hi\u0003\n"+
		"\u0005\u0000i\u0013\u0001\u0000\u0000\u0000jk\u0005\b\u0000\u0000kl\u0005"+
		"\u001a\u0000\u0000lm\u0003\u0016\u000b\u0000mn\u0005\u001b\u0000\u0000"+
		"no\u0005\t\u0000\u0000op\u0003\n\u0005\u0000p\u0015\u0001\u0000\u0000"+
		"\u0000qt\u0003\u0018\f\u0000rs\u0007\u0001\u0000\u0000su\u0003\u0018\f"+
		"\u0000tr\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000u\u0017\u0001"+
		"\u0000\u0000\u0000v{\u0003\u001a\r\u0000wx\u0007\u0002\u0000\u0000xz\u0003"+
		"\u001a\r\u0000yw\u0001\u0000\u0000\u0000z}\u0001\u0000\u0000\u0000{y\u0001"+
		"\u0000\u0000\u0000{|\u0001\u0000\u0000\u0000|\u0019\u0001\u0000\u0000"+
		"\u0000}{\u0001\u0000\u0000\u0000~\u0083\u0003\u001c\u000e\u0000\u007f"+
		"\u0080\u0007\u0003\u0000\u0000\u0080\u0082\u0003\u001c\u000e\u0000\u0081"+
		"\u007f\u0001\u0000\u0000\u0000\u0082\u0085\u0001\u0000\u0000\u0000\u0083"+
		"\u0081\u0001\u0000\u0000\u0000\u0083\u0084\u0001\u0000\u0000\u0000\u0084"+
		"\u001b\u0001\u0000\u0000\u0000\u0085\u0083\u0001\u0000\u0000\u0000\u0086"+
		"\u0088\u0007\u0002\u0000\u0000\u0087\u0086\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0001\u0000\u0000\u0000\u0088\u008f\u0001\u0000\u0000\u0000\u0089"+
		"\u0090\u0005\r\u0000\u0000\u008a\u0090\u0003\u001e\u000f\u0000\u008b\u008c"+
		"\u0005\u001a\u0000\u0000\u008c\u008d\u0003\u0016\u000b\u0000\u008d\u008e"+
		"\u0005\u001b\u0000\u0000\u008e\u0090\u0001\u0000\u0000\u0000\u008f\u0089"+
		"\u0001\u0000\u0000\u0000\u008f\u008a\u0001\u0000\u0000\u0000\u008f\u008b"+
		"\u0001\u0000\u0000\u0000\u0090\u001d\u0001\u0000\u0000\u0000\u0091\u0092"+
		"\u0007\u0004\u0000\u0000\u0092\u001f\u0001\u0000\u0000\u0000\n18AKUt{"+
		"\u0083\u0087\u008f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}