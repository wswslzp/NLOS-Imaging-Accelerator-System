// Generator : SpinalHDL v1.4.2    git head : d65b8c01ad060f0c2a8c80ed1b1e9da6e6240532
// Component : ExpFunc
// Git hash  : d151d69c0adb8fba5bc1372ea3c015e94de78c79
// Date      : 20/07/2020, 11:11:12
// Designer	: Zhengpeng Liao



module ExpFunc (
  input      [31:0]   io_data_in,
  output     [31:0]   io_data_out_real,
  output     [31:0]   io_data_out_imag,
  input               clk,
  input               reset
);
  wire       [31:0]   _zz_9;
  wire       [31:0]   _zz_10;
  wire       [31:0]   _zz_11;
  wire       [31:0]   _zz_12;
  wire       [31:0]   _zz_13;
  wire       [63:0]   _zz_14;
  wire       [31:0]   _zz_15;
  wire       [31:0]   _zz_16;
  wire       [63:0]   _zz_17;
  wire       [63:0]   _zz_18;
  wire       [63:0]   _zz_19;
  wire       [63:0]   _zz_20;
  wire       [63:0]   _zz_21;
  reg        [31:0]   _zz_22;
  reg        [31:0]   _zz_23;
  reg        [31:0]   _zz_24;
  reg        [31:0]   _zz_25;
  reg        [31:0]   _zz_26;
  reg        [31:0]   _zz_27;
  wire       [31:0]   fixTo_dout;
  wire       [31:0]   fixTo_1_dout;
  wire       [31:0]   fixTo_2_dout;
  wire       [31:0]   fixTo_3_dout;
  wire       [31:0]   fixTo_4_dout;
  wire       [31:0]   fixTo_5_dout;
  wire       [31:0]   fixTo_6_dout;
  wire       [31:0]   fixTo_7_dout;
  wire       [31:0]   fixTo_8_dout;
  wire       [31:0]   fixTo_9_dout;
  wire       [63:0]   fixTo_10_dout;
  wire       [63:0]   fixTo_11_dout;
  wire       [31:0]   fixTo_12_dout;
  wire       [31:0]   fixTo_13_dout;
  wire                _zz_28;
  wire                _zz_29;
  wire                _zz_30;
  wire       [31:0]   _zz_31;
  wire       [31:0]   _zz_32;
  wire       [31:0]   _zz_33;
  wire       [7:0]    _zz_34;
  wire       [7:0]    _zz_35;
  wire       [7:0]    _zz_36;
  wire       [7:0]    _zz_37;
  wire       [7:0]    _zz_38;
  wire       [7:0]    _zz_39;
  wire       [0:0]    _zz_40;
  wire       [7:0]    _zz_41;
  wire       [0:0]    _zz_42;
  wire       [7:0]    _zz_43;
  wire       [7:0]    _zz_44;
  wire       [0:0]    _zz_45;
  wire       [7:0]    _zz_46;
  wire       [0:0]    _zz_47;
  wire       [7:0]    _zz_48;
  wire       [7:0]    _zz_49;
  wire       [7:0]    _zz_50;
  wire       [0:0]    _zz_51;
  wire       [7:0]    _zz_52;
  wire       [0:0]    _zz_53;
  wire       [7:0]    _zz_54;
  wire       [7:0]    _zz_55;
  wire       [0:0]    _zz_56;
  wire       [7:0]    _zz_57;
  wire       [0:0]    _zz_58;
  wire       [7:0]    _zz_59;
  wire       [7:0]    _zz_60;
  wire       [7:0]    _zz_61;
  wire       [7:0]    _zz_62;
  wire       [0:0]    _zz_63;
  wire       [7:0]    _zz_64;
  wire       [0:0]    _zz_65;
  wire       [7:0]    _zz_66;
  wire       [7:0]    _zz_67;
  wire       [0:0]    _zz_68;
  wire       [7:0]    _zz_69;
  wire       [0:0]    _zz_70;
  wire       [7:0]    _zz_71;
  wire       [7:0]    _zz_72;
  wire       [7:0]    _zz_73;
  wire       [0:0]    _zz_74;
  wire       [7:0]    _zz_75;
  wire       [0:0]    _zz_76;
  wire       [7:0]    _zz_77;
  wire       [7:0]    _zz_78;
  wire       [0:0]    _zz_79;
  wire       [7:0]    _zz_80;
  wire       [0:0]    _zz_81;
  wire       [7:0]    _zz_82;
  wire       [7:0]    _zz_83;
  wire       [7:0]    _zz_84;
  wire       [7:0]    _zz_85;
  wire       [7:0]    _zz_86;
  wire       [0:0]    _zz_87;
  wire       [7:0]    _zz_88;
  wire       [0:0]    _zz_89;
  wire       [7:0]    _zz_90;
  wire       [7:0]    _zz_91;
  wire       [0:0]    _zz_92;
  wire       [7:0]    _zz_93;
  wire       [0:0]    _zz_94;
  wire       [7:0]    _zz_95;
  wire       [7:0]    _zz_96;
  wire       [7:0]    _zz_97;
  wire       [0:0]    _zz_98;
  wire       [7:0]    _zz_99;
  wire       [0:0]    _zz_100;
  wire       [7:0]    _zz_101;
  wire       [7:0]    _zz_102;
  wire       [0:0]    _zz_103;
  wire       [7:0]    _zz_104;
  wire       [0:0]    _zz_105;
  wire       [7:0]    _zz_106;
  wire       [7:0]    _zz_107;
  wire       [7:0]    _zz_108;
  wire       [7:0]    _zz_109;
  wire       [0:0]    _zz_110;
  wire       [7:0]    _zz_111;
  wire       [0:0]    _zz_112;
  wire       [7:0]    _zz_113;
  wire       [7:0]    _zz_114;
  wire       [0:0]    _zz_115;
  wire       [7:0]    _zz_116;
  wire       [0:0]    _zz_117;
  wire       [7:0]    _zz_118;
  wire       [7:0]    _zz_119;
  wire       [7:0]    _zz_120;
  wire       [0:0]    _zz_121;
  wire       [7:0]    _zz_122;
  wire       [0:0]    _zz_123;
  wire       [7:0]    _zz_124;
  wire       [7:0]    _zz_125;
  wire       [0:0]    _zz_126;
  wire       [7:0]    _zz_127;
  wire       [0:0]    _zz_128;
  wire       [7:0]    _zz_129;
  wire       [7:0]    _zz_130;
  wire       [7:0]    _zz_131;
  wire       [7:0]    _zz_132;
  wire       [7:0]    _zz_133;
  wire       [7:0]    _zz_134;
  wire       [0:0]    _zz_135;
  wire       [7:0]    _zz_136;
  wire       [0:0]    _zz_137;
  wire       [7:0]    _zz_138;
  wire       [7:0]    _zz_139;
  wire       [0:0]    _zz_140;
  wire       [7:0]    _zz_141;
  wire       [0:0]    _zz_142;
  wire       [7:0]    _zz_143;
  wire       [7:0]    _zz_144;
  wire       [7:0]    _zz_145;
  wire       [0:0]    _zz_146;
  wire       [7:0]    _zz_147;
  wire       [0:0]    _zz_148;
  wire       [7:0]    _zz_149;
  wire       [7:0]    _zz_150;
  wire       [0:0]    _zz_151;
  wire       [7:0]    _zz_152;
  wire       [0:0]    _zz_153;
  wire       [7:0]    _zz_154;
  wire       [7:0]    _zz_155;
  wire       [7:0]    _zz_156;
  wire       [7:0]    _zz_157;
  wire       [0:0]    _zz_158;
  wire       [7:0]    _zz_159;
  wire       [0:0]    _zz_160;
  wire       [7:0]    _zz_161;
  wire       [7:0]    _zz_162;
  wire       [0:0]    _zz_163;
  wire       [7:0]    _zz_164;
  wire       [0:0]    _zz_165;
  wire       [7:0]    _zz_166;
  wire       [7:0]    _zz_167;
  wire       [7:0]    _zz_168;
  wire       [0:0]    _zz_169;
  wire       [7:0]    _zz_170;
  wire       [0:0]    _zz_171;
  wire       [7:0]    _zz_172;
  wire       [7:0]    _zz_173;
  wire       [0:0]    _zz_174;
  wire       [7:0]    _zz_175;
  wire       [0:0]    _zz_176;
  wire       [7:0]    _zz_177;
  wire       [7:0]    _zz_178;
  wire       [7:0]    _zz_179;
  wire       [7:0]    _zz_180;
  wire       [7:0]    _zz_181;
  wire       [0:0]    _zz_182;
  wire       [7:0]    _zz_183;
  wire       [0:0]    _zz_184;
  wire       [7:0]    _zz_185;
  wire       [7:0]    _zz_186;
  wire       [0:0]    _zz_187;
  wire       [7:0]    _zz_188;
  wire       [0:0]    _zz_189;
  wire       [7:0]    _zz_190;
  wire       [7:0]    _zz_191;
  wire       [7:0]    _zz_192;
  wire       [0:0]    _zz_193;
  wire       [7:0]    _zz_194;
  wire       [0:0]    _zz_195;
  wire       [7:0]    _zz_196;
  wire       [7:0]    _zz_197;
  wire       [0:0]    _zz_198;
  wire       [7:0]    _zz_199;
  wire       [0:0]    _zz_200;
  wire       [7:0]    _zz_201;
  wire       [7:0]    _zz_202;
  wire       [7:0]    _zz_203;
  wire       [7:0]    _zz_204;
  wire       [0:0]    _zz_205;
  wire       [7:0]    _zz_206;
  wire       [0:0]    _zz_207;
  wire       [7:0]    _zz_208;
  wire       [7:0]    _zz_209;
  wire       [0:0]    _zz_210;
  wire       [7:0]    _zz_211;
  wire       [0:0]    _zz_212;
  wire       [7:0]    _zz_213;
  wire       [7:0]    _zz_214;
  wire       [7:0]    _zz_215;
  wire       [0:0]    _zz_216;
  wire       [7:0]    _zz_217;
  wire       [0:0]    _zz_218;
  wire       [7:0]    _zz_219;
  wire       [7:0]    _zz_220;
  wire       [0:0]    _zz_221;
  wire       [7:0]    _zz_222;
  wire       [0:0]    _zz_223;
  wire       [7:0]    _zz_224;
  wire       [7:0]    _zz_225;
  wire       [7:0]    _zz_226;
  wire       [7:0]    _zz_227;
  wire       [7:0]    _zz_228;
  wire       [7:0]    _zz_229;
  wire       [7:0]    _zz_230;
  wire       [0:0]    _zz_231;
  wire       [7:0]    _zz_232;
  wire       [0:0]    _zz_233;
  wire       [7:0]    _zz_234;
  wire       [7:0]    _zz_235;
  wire       [0:0]    _zz_236;
  wire       [7:0]    _zz_237;
  wire       [0:0]    _zz_238;
  wire       [7:0]    _zz_239;
  wire       [7:0]    _zz_240;
  wire       [7:0]    _zz_241;
  wire       [0:0]    _zz_242;
  wire       [7:0]    _zz_243;
  wire       [0:0]    _zz_244;
  wire       [7:0]    _zz_245;
  wire       [7:0]    _zz_246;
  wire       [0:0]    _zz_247;
  wire       [7:0]    _zz_248;
  wire       [0:0]    _zz_249;
  wire       [7:0]    _zz_250;
  wire       [7:0]    _zz_251;
  wire       [7:0]    _zz_252;
  wire       [7:0]    _zz_253;
  wire       [0:0]    _zz_254;
  wire       [7:0]    _zz_255;
  wire       [0:0]    _zz_256;
  wire       [7:0]    _zz_257;
  wire       [7:0]    _zz_258;
  wire       [0:0]    _zz_259;
  wire       [7:0]    _zz_260;
  wire       [0:0]    _zz_261;
  wire       [7:0]    _zz_262;
  wire       [7:0]    _zz_263;
  wire       [7:0]    _zz_264;
  wire       [0:0]    _zz_265;
  wire       [7:0]    _zz_266;
  wire       [0:0]    _zz_267;
  wire       [7:0]    _zz_268;
  wire       [7:0]    _zz_269;
  wire       [0:0]    _zz_270;
  wire       [7:0]    _zz_271;
  wire       [0:0]    _zz_272;
  wire       [7:0]    _zz_273;
  wire       [7:0]    _zz_274;
  wire       [7:0]    _zz_275;
  wire       [7:0]    _zz_276;
  wire       [7:0]    _zz_277;
  wire       [0:0]    _zz_278;
  wire       [7:0]    _zz_279;
  wire       [0:0]    _zz_280;
  wire       [7:0]    _zz_281;
  wire       [7:0]    _zz_282;
  wire       [0:0]    _zz_283;
  wire       [7:0]    _zz_284;
  wire       [0:0]    _zz_285;
  wire       [7:0]    _zz_286;
  wire       [7:0]    _zz_287;
  wire       [7:0]    _zz_288;
  wire       [0:0]    _zz_289;
  wire       [7:0]    _zz_290;
  wire       [0:0]    _zz_291;
  wire       [7:0]    _zz_292;
  wire       [7:0]    _zz_293;
  wire       [0:0]    _zz_294;
  wire       [7:0]    _zz_295;
  wire       [0:0]    _zz_296;
  wire       [7:0]    _zz_297;
  wire       [7:0]    _zz_298;
  wire       [7:0]    _zz_299;
  wire       [7:0]    _zz_300;
  wire       [0:0]    _zz_301;
  wire       [7:0]    _zz_302;
  wire       [0:0]    _zz_303;
  wire       [7:0]    _zz_304;
  wire       [7:0]    _zz_305;
  wire       [0:0]    _zz_306;
  wire       [7:0]    _zz_307;
  wire       [0:0]    _zz_308;
  wire       [7:0]    _zz_309;
  wire       [7:0]    _zz_310;
  wire       [7:0]    _zz_311;
  wire       [0:0]    _zz_312;
  wire       [7:0]    _zz_313;
  wire       [0:0]    _zz_314;
  wire       [7:0]    _zz_315;
  wire       [7:0]    _zz_316;
  wire       [0:0]    _zz_317;
  wire       [7:0]    _zz_318;
  wire       [0:0]    _zz_319;
  wire       [7:0]    _zz_320;
  wire       [7:0]    _zz_321;
  wire       [7:0]    _zz_322;
  wire       [7:0]    _zz_323;
  wire       [7:0]    _zz_324;
  wire       [7:0]    _zz_325;
  wire       [0:0]    _zz_326;
  wire       [7:0]    _zz_327;
  wire       [0:0]    _zz_328;
  wire       [7:0]    _zz_329;
  wire       [7:0]    _zz_330;
  wire       [0:0]    _zz_331;
  wire       [7:0]    _zz_332;
  wire       [0:0]    _zz_333;
  wire       [7:0]    _zz_334;
  wire       [7:0]    _zz_335;
  wire       [7:0]    _zz_336;
  wire       [0:0]    _zz_337;
  wire       [7:0]    _zz_338;
  wire       [0:0]    _zz_339;
  wire       [7:0]    _zz_340;
  wire       [7:0]    _zz_341;
  wire       [0:0]    _zz_342;
  wire       [7:0]    _zz_343;
  wire       [0:0]    _zz_344;
  wire       [7:0]    _zz_345;
  wire       [7:0]    _zz_346;
  wire       [7:0]    _zz_347;
  wire       [7:0]    _zz_348;
  wire       [0:0]    _zz_349;
  wire       [7:0]    _zz_350;
  wire       [0:0]    _zz_351;
  wire       [7:0]    _zz_352;
  wire       [7:0]    _zz_353;
  wire       [0:0]    _zz_354;
  wire       [7:0]    _zz_355;
  wire       [0:0]    _zz_356;
  wire       [7:0]    _zz_357;
  wire       [7:0]    _zz_358;
  wire       [7:0]    _zz_359;
  wire       [0:0]    _zz_360;
  wire       [7:0]    _zz_361;
  wire       [0:0]    _zz_362;
  wire       [7:0]    _zz_363;
  wire       [7:0]    _zz_364;
  wire       [0:0]    _zz_365;
  wire       [7:0]    _zz_366;
  wire       [0:0]    _zz_367;
  wire       [7:0]    _zz_368;
  wire       [7:0]    _zz_369;
  wire       [7:0]    _zz_370;
  wire       [7:0]    _zz_371;
  wire       [7:0]    _zz_372;
  wire       [0:0]    _zz_373;
  wire       [7:0]    _zz_374;
  wire       [0:0]    _zz_375;
  wire       [7:0]    _zz_376;
  wire       [7:0]    _zz_377;
  wire       [0:0]    _zz_378;
  wire       [7:0]    _zz_379;
  wire       [0:0]    _zz_380;
  wire       [7:0]    _zz_381;
  wire       [7:0]    _zz_382;
  wire       [7:0]    _zz_383;
  wire       [0:0]    _zz_384;
  wire       [7:0]    _zz_385;
  wire       [0:0]    _zz_386;
  wire       [7:0]    _zz_387;
  wire       [7:0]    _zz_388;
  wire       [0:0]    _zz_389;
  wire       [7:0]    _zz_390;
  wire       [0:0]    _zz_391;
  wire       [7:0]    _zz_392;
  wire       [7:0]    _zz_393;
  wire       [7:0]    _zz_394;
  wire       [7:0]    _zz_395;
  wire       [0:0]    _zz_396;
  wire       [7:0]    _zz_397;
  wire       [0:0]    _zz_398;
  wire       [7:0]    _zz_399;
  wire       [7:0]    _zz_400;
  wire       [0:0]    _zz_401;
  wire       [7:0]    _zz_402;
  wire       [0:0]    _zz_403;
  wire       [7:0]    _zz_404;
  wire       [7:0]    _zz_405;
  wire       [7:0]    _zz_406;
  wire       [0:0]    _zz_407;
  wire       [7:0]    _zz_408;
  wire       [0:0]    _zz_409;
  wire       [7:0]    _zz_410;
  wire       [7:0]    _zz_411;
  wire       [0:0]    _zz_412;
  wire       [7:0]    _zz_413;
  wire       [0:0]    _zz_414;
  wire       [7:0]    _zz_415;
  wire       [31:0]   _zz_416;
  wire       [31:0]   _zz_417;
  wire       [31:0]   _zz_418;
  wire       [31:0]   _zz_419;
  wire       [31:0]   _zz_420;
  wire       [31:0]   _zz_421;
  wire       [63:0]   _zz_422;
  wire       [31:0]   _zz_423;
  wire       [31:0]   _zz_424;
  wire       [31:0]   _zz_425;
  wire       [31:0]   _zz_426;
  wire       [63:0]   _zz_427;
  wire       [31:0]   _zz_428;
  wire       [31:0]   _zz_429;
  wire       [31:0]   _zz_430;
  wire       [31:0]   _zz_431;
  wire       [63:0]   _zz_432;
  wire       [63:0]   _zz_433;
  wire       [31:0]   _zz_434;
  wire       [63:0]   _zz_435;
  wire       [63:0]   _zz_436;
  wire       [31:0]   _zz_437;
  wire       [63:0]   _zz_438;
  wire       [63:0]   _zz_439;
  wire       [47:0]   _zz_440;
  wire       [63:0]   _zz_441;
  wire       [63:0]   _zz_442;
  wire       [63:0]   _zz_443;
  wire       [47:0]   _zz_444;
  wire       [63:0]   _zz_445;
  wire       [31:0]   _zz_446;
  wire       [31:0]   _zz_447;
  wire       [31:0]   fx_period;
  wire       [31:0]   _zz_1;
  reg        [31:0]   indx;
  wire       [31:0]   pfunc_tb_0_real;
  wire       [31:0]   pfunc_tb_0_imag;
  wire       [31:0]   pfunc_tb_1_real;
  wire       [31:0]   pfunc_tb_1_imag;
  wire       [31:0]   pfunc_tb_2_real;
  wire       [31:0]   pfunc_tb_2_imag;
  wire       [31:0]   pfunc_tb_3_real;
  wire       [31:0]   pfunc_tb_3_imag;
  wire       [31:0]   pfunc_tb_4_real;
  wire       [31:0]   pfunc_tb_4_imag;
  wire       [31:0]   pfunc_tb_5_real;
  wire       [31:0]   pfunc_tb_5_imag;
  wire       [31:0]   pfunc_tb_6_real;
  wire       [31:0]   pfunc_tb_6_imag;
  wire       [31:0]   pfunc_tb_7_real;
  wire       [31:0]   pfunc_tb_7_imag;
  wire       [31:0]   pfunc_tb_8_real;
  wire       [31:0]   pfunc_tb_8_imag;
  wire       [31:0]   pfunc_tb_9_real;
  wire       [31:0]   pfunc_tb_9_imag;
  wire       [31:0]   pfunc_tb_10_real;
  wire       [31:0]   pfunc_tb_10_imag;
  wire       [31:0]   pfunc_tb_11_real;
  wire       [31:0]   pfunc_tb_11_imag;
  wire       [31:0]   pfunc_tb_12_real;
  wire       [31:0]   pfunc_tb_12_imag;
  wire       [31:0]   pfunc_tb_13_real;
  wire       [31:0]   pfunc_tb_13_imag;
  wire       [31:0]   pfunc_tb_14_real;
  wire       [31:0]   pfunc_tb_14_imag;
  wire       [31:0]   pfunc_tb_15_real;
  wire       [31:0]   pfunc_tb_15_imag;
  wire       [31:0]   pfunc_tb_16_real;
  wire       [31:0]   pfunc_tb_16_imag;
  wire       [31:0]   pfunc_tb_17_real;
  wire       [31:0]   pfunc_tb_17_imag;
  wire       [31:0]   pfunc_tb_18_real;
  wire       [31:0]   pfunc_tb_18_imag;
  wire       [31:0]   pfunc_tb_19_real;
  wire       [31:0]   pfunc_tb_19_imag;
  wire       [31:0]   pfunc_tb_20_real;
  wire       [31:0]   pfunc_tb_20_imag;
  wire       [31:0]   pfunc_tb_21_real;
  wire       [31:0]   pfunc_tb_21_imag;
  wire       [31:0]   pfunc_tb_22_real;
  wire       [31:0]   pfunc_tb_22_imag;
  wire       [31:0]   pfunc_tb_23_real;
  wire       [31:0]   pfunc_tb_23_imag;
  wire       [31:0]   pfunc_tb_24_real;
  wire       [31:0]   pfunc_tb_24_imag;
  wire       [31:0]   pfunc_tb_25_real;
  wire       [31:0]   pfunc_tb_25_imag;
  wire       [31:0]   pfunc_tb_26_real;
  wire       [31:0]   pfunc_tb_26_imag;
  wire       [31:0]   pfunc_tb_27_real;
  wire       [31:0]   pfunc_tb_27_imag;
  wire       [31:0]   pfunc_tb_28_real;
  wire       [31:0]   pfunc_tb_28_imag;
  wire       [31:0]   pfunc_tb_29_real;
  wire       [31:0]   pfunc_tb_29_imag;
  wire       [31:0]   pfunc_tb_30_real;
  wire       [31:0]   pfunc_tb_30_imag;
  wire       [31:0]   pfunc_tb_31_real;
  wire       [31:0]   pfunc_tb_31_imag;
  wire       [31:0]   pfunc_tb_32_real;
  wire       [31:0]   pfunc_tb_32_imag;
  wire       [31:0]   pfunc_tb_33_real;
  wire       [31:0]   pfunc_tb_33_imag;
  wire       [31:0]   pfunc_tb_34_real;
  wire       [31:0]   pfunc_tb_34_imag;
  wire       [31:0]   pfunc_tb_35_real;
  wire       [31:0]   pfunc_tb_35_imag;
  wire       [31:0]   pfunc_tb_36_real;
  wire       [31:0]   pfunc_tb_36_imag;
  wire       [31:0]   pfunc_tb_37_real;
  wire       [31:0]   pfunc_tb_37_imag;
  wire       [31:0]   pfunc_tb_38_real;
  wire       [31:0]   pfunc_tb_38_imag;
  wire       [31:0]   pfunc_tb_39_real;
  wire       [31:0]   pfunc_tb_39_imag;
  wire       [31:0]   pfunc_tb_40_real;
  wire       [31:0]   pfunc_tb_40_imag;
  wire       [31:0]   pfunc_tb_41_real;
  wire       [31:0]   pfunc_tb_41_imag;
  wire       [31:0]   pfunc_tb_42_real;
  wire       [31:0]   pfunc_tb_42_imag;
  wire       [31:0]   pfunc_tb_43_real;
  wire       [31:0]   pfunc_tb_43_imag;
  wire       [31:0]   pfunc_tb_44_real;
  wire       [31:0]   pfunc_tb_44_imag;
  wire       [31:0]   pfunc_tb_45_real;
  wire       [31:0]   pfunc_tb_45_imag;
  wire       [31:0]   pfunc_tb_46_real;
  wire       [31:0]   pfunc_tb_46_imag;
  wire       [31:0]   pfunc_tb_47_real;
  wire       [31:0]   pfunc_tb_47_imag;
  wire       [31:0]   pfunc_tb_48_real;
  wire       [31:0]   pfunc_tb_48_imag;
  wire       [31:0]   pfunc_tb_49_real;
  wire       [31:0]   pfunc_tb_49_imag;
  wire       [31:0]   pfunc_tb_50_real;
  wire       [31:0]   pfunc_tb_50_imag;
  wire       [31:0]   pfunc_tb_51_real;
  wire       [31:0]   pfunc_tb_51_imag;
  wire       [31:0]   pfunc_tb_52_real;
  wire       [31:0]   pfunc_tb_52_imag;
  wire       [31:0]   pfunc_tb_53_real;
  wire       [31:0]   pfunc_tb_53_imag;
  wire       [31:0]   pfunc_tb_54_real;
  wire       [31:0]   pfunc_tb_54_imag;
  wire       [31:0]   pfunc_tb_55_real;
  wire       [31:0]   pfunc_tb_55_imag;
  wire       [31:0]   pfunc_tb_56_real;
  wire       [31:0]   pfunc_tb_56_imag;
  wire       [31:0]   pfunc_tb_57_real;
  wire       [31:0]   pfunc_tb_57_imag;
  wire       [31:0]   pfunc_tb_58_real;
  wire       [31:0]   pfunc_tb_58_imag;
  wire       [31:0]   pfunc_tb_59_real;
  wire       [31:0]   pfunc_tb_59_imag;
  wire       [31:0]   pfunc_tb_60_real;
  wire       [31:0]   pfunc_tb_60_imag;
  wire       [31:0]   pfunc_tb_61_real;
  wire       [31:0]   pfunc_tb_61_imag;
  wire       [31:0]   pfunc_tb_62_real;
  wire       [31:0]   pfunc_tb_62_imag;
  wire       [31:0]   pfunc_tb_63_real;
  wire       [31:0]   pfunc_tb_63_imag;
  wire       [31:0]   pfunc_tb_64_real;
  wire       [31:0]   pfunc_tb_64_imag;
  wire       [31:0]   pfunc_tb_65_real;
  wire       [31:0]   pfunc_tb_65_imag;
  wire       [31:0]   pfunc_tb_66_real;
  wire       [31:0]   pfunc_tb_66_imag;
  wire       [31:0]   pfunc_tb_67_real;
  wire       [31:0]   pfunc_tb_67_imag;
  wire       [31:0]   pfunc_tb_68_real;
  wire       [31:0]   pfunc_tb_68_imag;
  wire       [31:0]   pfunc_tb_69_real;
  wire       [31:0]   pfunc_tb_69_imag;
  wire       [31:0]   pfunc_tb_70_real;
  wire       [31:0]   pfunc_tb_70_imag;
  wire       [31:0]   pfunc_tb_71_real;
  wire       [31:0]   pfunc_tb_71_imag;
  wire       [31:0]   pfunc_tb_72_real;
  wire       [31:0]   pfunc_tb_72_imag;
  wire       [31:0]   pfunc_tb_73_real;
  wire       [31:0]   pfunc_tb_73_imag;
  wire       [31:0]   pfunc_tb_74_real;
  wire       [31:0]   pfunc_tb_74_imag;
  wire       [31:0]   pfunc_tb_75_real;
  wire       [31:0]   pfunc_tb_75_imag;
  wire       [31:0]   pfunc_tb_76_real;
  wire       [31:0]   pfunc_tb_76_imag;
  wire       [31:0]   pfunc_tb_77_real;
  wire       [31:0]   pfunc_tb_77_imag;
  wire       [31:0]   pfunc_tb_78_real;
  wire       [31:0]   pfunc_tb_78_imag;
  wire       [31:0]   pfunc_tb_79_real;
  wire       [31:0]   pfunc_tb_79_imag;
  wire       [31:0]   pfunc_tb_80_real;
  wire       [31:0]   pfunc_tb_80_imag;
  wire       [31:0]   pfunc_tb_81_real;
  wire       [31:0]   pfunc_tb_81_imag;
  wire       [31:0]   pfunc_tb_82_real;
  wire       [31:0]   pfunc_tb_82_imag;
  wire       [31:0]   pfunc_tb_83_real;
  wire       [31:0]   pfunc_tb_83_imag;
  wire       [31:0]   pfunc_tb_84_real;
  wire       [31:0]   pfunc_tb_84_imag;
  wire       [31:0]   pfunc_tb_85_real;
  wire       [31:0]   pfunc_tb_85_imag;
  wire       [31:0]   pfunc_tb_86_real;
  wire       [31:0]   pfunc_tb_86_imag;
  wire       [31:0]   pfunc_tb_87_real;
  wire       [31:0]   pfunc_tb_87_imag;
  wire       [31:0]   pfunc_tb_88_real;
  wire       [31:0]   pfunc_tb_88_imag;
  wire       [31:0]   pfunc_tb_89_real;
  wire       [31:0]   pfunc_tb_89_imag;
  wire       [31:0]   pfunc_tb_90_real;
  wire       [31:0]   pfunc_tb_90_imag;
  wire       [31:0]   pfunc_tb_91_real;
  wire       [31:0]   pfunc_tb_91_imag;
  wire       [31:0]   pfunc_tb_92_real;
  wire       [31:0]   pfunc_tb_92_imag;
  wire       [31:0]   pfunc_tb_93_real;
  wire       [31:0]   pfunc_tb_93_imag;
  wire       [31:0]   pfunc_tb_94_real;
  wire       [31:0]   pfunc_tb_94_imag;
  wire       [31:0]   pfunc_tb_95_real;
  wire       [31:0]   pfunc_tb_95_imag;
  wire       [31:0]   pfunc_tb_96_real;
  wire       [31:0]   pfunc_tb_96_imag;
  wire       [31:0]   pfunc_tb_97_real;
  wire       [31:0]   pfunc_tb_97_imag;
  wire       [31:0]   pfunc_tb_98_real;
  wire       [31:0]   pfunc_tb_98_imag;
  wire       [31:0]   pfunc_tb_99_real;
  wire       [31:0]   pfunc_tb_99_imag;
  wire       [31:0]   pfunc_tb_100_real;
  wire       [31:0]   pfunc_tb_100_imag;
  wire       [31:0]   pfunc_tb_101_real;
  wire       [31:0]   pfunc_tb_101_imag;
  wire       [31:0]   pfunc_tb_102_real;
  wire       [31:0]   pfunc_tb_102_imag;
  wire       [31:0]   pfunc_tb_103_real;
  wire       [31:0]   pfunc_tb_103_imag;
  wire       [31:0]   pfunc_tb_104_real;
  wire       [31:0]   pfunc_tb_104_imag;
  wire       [31:0]   pfunc_tb_105_real;
  wire       [31:0]   pfunc_tb_105_imag;
  wire       [31:0]   pfunc_tb_106_real;
  wire       [31:0]   pfunc_tb_106_imag;
  wire       [31:0]   pfunc_tb_107_real;
  wire       [31:0]   pfunc_tb_107_imag;
  wire       [31:0]   pfunc_tb_108_real;
  wire       [31:0]   pfunc_tb_108_imag;
  wire       [31:0]   pfunc_tb_109_real;
  wire       [31:0]   pfunc_tb_109_imag;
  wire       [31:0]   pfunc_tb_110_real;
  wire       [31:0]   pfunc_tb_110_imag;
  wire       [31:0]   pfunc_tb_111_real;
  wire       [31:0]   pfunc_tb_111_imag;
  wire       [31:0]   pfunc_tb_112_real;
  wire       [31:0]   pfunc_tb_112_imag;
  wire       [31:0]   pfunc_tb_113_real;
  wire       [31:0]   pfunc_tb_113_imag;
  wire       [31:0]   pfunc_tb_114_real;
  wire       [31:0]   pfunc_tb_114_imag;
  wire       [31:0]   pfunc_tb_115_real;
  wire       [31:0]   pfunc_tb_115_imag;
  wire       [31:0]   pfunc_tb_116_real;
  wire       [31:0]   pfunc_tb_116_imag;
  wire       [31:0]   pfunc_tb_117_real;
  wire       [31:0]   pfunc_tb_117_imag;
  wire       [31:0]   pfunc_tb_118_real;
  wire       [31:0]   pfunc_tb_118_imag;
  wire       [31:0]   pfunc_tb_119_real;
  wire       [31:0]   pfunc_tb_119_imag;
  wire       [31:0]   pfunc_tb_120_real;
  wire       [31:0]   pfunc_tb_120_imag;
  wire       [31:0]   pfunc_tb_121_real;
  wire       [31:0]   pfunc_tb_121_imag;
  wire       [31:0]   pfunc_tb_122_real;
  wire       [31:0]   pfunc_tb_122_imag;
  wire       [31:0]   pfunc_tb_123_real;
  wire       [31:0]   pfunc_tb_123_imag;
  wire       [31:0]   pfunc_tb_124_real;
  wire       [31:0]   pfunc_tb_124_imag;
  wire       [31:0]   pfunc_tb_125_real;
  wire       [31:0]   pfunc_tb_125_imag;
  wire       [31:0]   pfunc_tb_126_real;
  wire       [31:0]   pfunc_tb_126_imag;
  wire       [31:0]   pfunc_tb_127_real;
  wire       [31:0]   pfunc_tb_127_imag;
  wire       [31:0]   pindx_tb_0;
  wire       [31:0]   pindx_tb_1;
  wire       [31:0]   pindx_tb_2;
  wire       [31:0]   pindx_tb_3;
  wire       [31:0]   pindx_tb_4;
  wire       [31:0]   pindx_tb_5;
  wire       [31:0]   pindx_tb_6;
  wire       [31:0]   pindx_tb_7;
  wire       [31:0]   pindx_tb_8;
  wire       [31:0]   pindx_tb_9;
  wire       [31:0]   pindx_tb_10;
  wire       [31:0]   pindx_tb_11;
  wire       [31:0]   pindx_tb_12;
  wire       [31:0]   pindx_tb_13;
  wire       [31:0]   pindx_tb_14;
  wire       [31:0]   pindx_tb_15;
  wire       [31:0]   pindx_tb_16;
  wire       [31:0]   pindx_tb_17;
  wire       [31:0]   pindx_tb_18;
  wire       [31:0]   pindx_tb_19;
  wire       [31:0]   pindx_tb_20;
  wire       [31:0]   pindx_tb_21;
  wire       [31:0]   pindx_tb_22;
  wire       [31:0]   pindx_tb_23;
  wire       [31:0]   pindx_tb_24;
  wire       [31:0]   pindx_tb_25;
  wire       [31:0]   pindx_tb_26;
  wire       [31:0]   pindx_tb_27;
  wire       [31:0]   pindx_tb_28;
  wire       [31:0]   pindx_tb_29;
  wire       [31:0]   pindx_tb_30;
  wire       [31:0]   pindx_tb_31;
  wire       [31:0]   pindx_tb_32;
  wire       [31:0]   pindx_tb_33;
  wire       [31:0]   pindx_tb_34;
  wire       [31:0]   pindx_tb_35;
  wire       [31:0]   pindx_tb_36;
  wire       [31:0]   pindx_tb_37;
  wire       [31:0]   pindx_tb_38;
  wire       [31:0]   pindx_tb_39;
  wire       [31:0]   pindx_tb_40;
  wire       [31:0]   pindx_tb_41;
  wire       [31:0]   pindx_tb_42;
  wire       [31:0]   pindx_tb_43;
  wire       [31:0]   pindx_tb_44;
  wire       [31:0]   pindx_tb_45;
  wire       [31:0]   pindx_tb_46;
  wire       [31:0]   pindx_tb_47;
  wire       [31:0]   pindx_tb_48;
  wire       [31:0]   pindx_tb_49;
  wire       [31:0]   pindx_tb_50;
  wire       [31:0]   pindx_tb_51;
  wire       [31:0]   pindx_tb_52;
  wire       [31:0]   pindx_tb_53;
  wire       [31:0]   pindx_tb_54;
  wire       [31:0]   pindx_tb_55;
  wire       [31:0]   pindx_tb_56;
  wire       [31:0]   pindx_tb_57;
  wire       [31:0]   pindx_tb_58;
  wire       [31:0]   pindx_tb_59;
  wire       [31:0]   pindx_tb_60;
  wire       [31:0]   pindx_tb_61;
  wire       [31:0]   pindx_tb_62;
  wire       [31:0]   pindx_tb_63;
  wire       [31:0]   pindx_tb_64;
  wire       [31:0]   pindx_tb_65;
  wire       [31:0]   pindx_tb_66;
  wire       [31:0]   pindx_tb_67;
  wire       [31:0]   pindx_tb_68;
  wire       [31:0]   pindx_tb_69;
  wire       [31:0]   pindx_tb_70;
  wire       [31:0]   pindx_tb_71;
  wire       [31:0]   pindx_tb_72;
  wire       [31:0]   pindx_tb_73;
  wire       [31:0]   pindx_tb_74;
  wire       [31:0]   pindx_tb_75;
  wire       [31:0]   pindx_tb_76;
  wire       [31:0]   pindx_tb_77;
  wire       [31:0]   pindx_tb_78;
  wire       [31:0]   pindx_tb_79;
  wire       [31:0]   pindx_tb_80;
  wire       [31:0]   pindx_tb_81;
  wire       [31:0]   pindx_tb_82;
  wire       [31:0]   pindx_tb_83;
  wire       [31:0]   pindx_tb_84;
  wire       [31:0]   pindx_tb_85;
  wire       [31:0]   pindx_tb_86;
  wire       [31:0]   pindx_tb_87;
  wire       [31:0]   pindx_tb_88;
  wire       [31:0]   pindx_tb_89;
  wire       [31:0]   pindx_tb_90;
  wire       [31:0]   pindx_tb_91;
  wire       [31:0]   pindx_tb_92;
  wire       [31:0]   pindx_tb_93;
  wire       [31:0]   pindx_tb_94;
  wire       [31:0]   pindx_tb_95;
  wire       [31:0]   pindx_tb_96;
  wire       [31:0]   pindx_tb_97;
  wire       [31:0]   pindx_tb_98;
  wire       [31:0]   pindx_tb_99;
  wire       [31:0]   pindx_tb_100;
  wire       [31:0]   pindx_tb_101;
  wire       [31:0]   pindx_tb_102;
  wire       [31:0]   pindx_tb_103;
  wire       [31:0]   pindx_tb_104;
  wire       [31:0]   pindx_tb_105;
  wire       [31:0]   pindx_tb_106;
  wire       [31:0]   pindx_tb_107;
  wire       [31:0]   pindx_tb_108;
  wire       [31:0]   pindx_tb_109;
  wire       [31:0]   pindx_tb_110;
  wire       [31:0]   pindx_tb_111;
  wire       [31:0]   pindx_tb_112;
  wire       [31:0]   pindx_tb_113;
  wire       [31:0]   pindx_tb_114;
  wire       [31:0]   pindx_tb_115;
  wire       [31:0]   pindx_tb_116;
  wire       [31:0]   pindx_tb_117;
  wire       [31:0]   pindx_tb_118;
  wire       [31:0]   pindx_tb_119;
  wire       [31:0]   pindx_tb_120;
  wire       [31:0]   pindx_tb_121;
  wire       [31:0]   pindx_tb_122;
  wire       [31:0]   pindx_tb_123;
  wire       [31:0]   pindx_tb_124;
  wire       [31:0]   pindx_tb_125;
  wire       [31:0]   pindx_tb_126;
  wire       [31:0]   pindx_tb_127;
  wire                idx_comp_0;
  wire                idx_comp_1;
  wire                idx_comp_2;
  wire                idx_comp_3;
  wire                idx_comp_4;
  wire                idx_comp_5;
  wire                idx_comp_6;
  wire                idx_comp_7;
  wire                idx_comp_8;
  wire                idx_comp_9;
  wire                idx_comp_10;
  wire                idx_comp_11;
  wire                idx_comp_12;
  wire                idx_comp_13;
  wire                idx_comp_14;
  wire                idx_comp_15;
  wire                idx_comp_16;
  wire                idx_comp_17;
  wire                idx_comp_18;
  wire                idx_comp_19;
  wire                idx_comp_20;
  wire                idx_comp_21;
  wire                idx_comp_22;
  wire                idx_comp_23;
  wire                idx_comp_24;
  wire                idx_comp_25;
  wire                idx_comp_26;
  wire                idx_comp_27;
  wire                idx_comp_28;
  wire                idx_comp_29;
  wire                idx_comp_30;
  wire                idx_comp_31;
  wire                idx_comp_32;
  wire                idx_comp_33;
  wire                idx_comp_34;
  wire                idx_comp_35;
  wire                idx_comp_36;
  wire                idx_comp_37;
  wire                idx_comp_38;
  wire                idx_comp_39;
  wire                idx_comp_40;
  wire                idx_comp_41;
  wire                idx_comp_42;
  wire                idx_comp_43;
  wire                idx_comp_44;
  wire                idx_comp_45;
  wire                idx_comp_46;
  wire                idx_comp_47;
  wire                idx_comp_48;
  wire                idx_comp_49;
  wire                idx_comp_50;
  wire                idx_comp_51;
  wire                idx_comp_52;
  wire                idx_comp_53;
  wire                idx_comp_54;
  wire                idx_comp_55;
  wire                idx_comp_56;
  wire                idx_comp_57;
  wire                idx_comp_58;
  wire                idx_comp_59;
  wire                idx_comp_60;
  wire                idx_comp_61;
  wire                idx_comp_62;
  wire                idx_comp_63;
  wire                idx_comp_64;
  wire                idx_comp_65;
  wire                idx_comp_66;
  wire                idx_comp_67;
  wire                idx_comp_68;
  wire                idx_comp_69;
  wire                idx_comp_70;
  wire                idx_comp_71;
  wire                idx_comp_72;
  wire                idx_comp_73;
  wire                idx_comp_74;
  wire                idx_comp_75;
  wire                idx_comp_76;
  wire                idx_comp_77;
  wire                idx_comp_78;
  wire                idx_comp_79;
  wire                idx_comp_80;
  wire                idx_comp_81;
  wire                idx_comp_82;
  wire                idx_comp_83;
  wire                idx_comp_84;
  wire                idx_comp_85;
  wire                idx_comp_86;
  wire                idx_comp_87;
  wire                idx_comp_88;
  wire                idx_comp_89;
  wire                idx_comp_90;
  wire                idx_comp_91;
  wire                idx_comp_92;
  wire                idx_comp_93;
  wire                idx_comp_94;
  wire                idx_comp_95;
  wire                idx_comp_96;
  wire                idx_comp_97;
  wire                idx_comp_98;
  wire                idx_comp_99;
  wire                idx_comp_100;
  wire                idx_comp_101;
  wire                idx_comp_102;
  wire                idx_comp_103;
  wire                idx_comp_104;
  wire                idx_comp_105;
  wire                idx_comp_106;
  wire                idx_comp_107;
  wire                idx_comp_108;
  wire                idx_comp_109;
  wire                idx_comp_110;
  wire                idx_comp_111;
  wire                idx_comp_112;
  wire                idx_comp_113;
  wire                idx_comp_114;
  wire                idx_comp_115;
  wire                idx_comp_116;
  wire                idx_comp_117;
  wire                idx_comp_118;
  wire                idx_comp_119;
  wire                idx_comp_120;
  wire                idx_comp_121;
  wire                idx_comp_122;
  wire                idx_comp_123;
  wire                idx_comp_124;
  wire                idx_comp_125;
  wire                idx_comp_126;
  wire                idx_comp_127;
  reg        [127:0]  idx_comp_vec;
  wire       [127:0]  _zz_2;
  wire       [7:0]    ret;
  reg        [7:0]    lzc_t;
  wire       [6:0]    lzc;
  reg        [31:0]   exp_func_value_real;
  reg        [31:0]   exp_func_value_imag;
  wire       [6:0]    position;
  wire       [31:0]   x1;
  wire       [31:0]   x2;
  wire       [31:0]   y1_real;
  wire       [31:0]   y1_imag;
  wire       [31:0]   y2_real;
  wire       [31:0]   y2_imag;
  reg        [31:0]   indx_regNext;
  wire       [31:0]   xd;
  wire       [31:0]   yd_real;
  wire       [31:0]   yd_imag;
  reg        [31:0]   k_real;
  reg        [31:0]   k_imag;
  wire       [31:0]   _zz_3;
  wire       [31:0]   deltax_real;
  wire       [31:0]   deltax_imag;
  wire       [63:0]   _zz_4;
  wire       [63:0]   pre_exp_indx_real;
  wire       [63:0]   pre_exp_indx_imag;
  reg        [63:0]   pre_exp_indx_regNext_real;
  reg        [63:0]   pre_exp_indx_regNext_imag;
  reg        [31:0]   pfunc_tb_127_regNext_real;
  reg        [31:0]   pfunc_tb_127_regNext_imag;
  reg        [31:0]   _zz_5;
  reg        [31:0]   _zz_6;
  reg        [31:0]   pfunc_tb_0_regNext_real;
  reg        [31:0]   pfunc_tb_0_regNext_imag;
  reg        [31:0]   _zz_7;
  reg        [31:0]   _zz_8;

  assign _zz_28 = (xd == 32'h0);
  assign _zz_29 = (lzc != 7'h0);
  assign _zz_30 = (lzc_t[7] == 1'b1);
  assign _zz_31 = _zz_32;
  assign _zz_32 = ($signed(fixTo_dout) % $signed(_zz_33));
  assign _zz_33 = fx_period;
  assign _zz_34 = (_zz_35 + _zz_130);
  assign _zz_35 = (_zz_36 + _zz_83);
  assign _zz_36 = (_zz_37 + _zz_60);
  assign _zz_37 = (_zz_38 + _zz_49);
  assign _zz_38 = (_zz_39 + _zz_44);
  assign _zz_39 = (_zz_41 + _zz_43);
  assign _zz_40 = _zz_2[0];
  assign _zz_41 = {7'd0, _zz_40};
  assign _zz_42 = _zz_2[1];
  assign _zz_43 = {7'd0, _zz_42};
  assign _zz_44 = (_zz_46 + _zz_48);
  assign _zz_45 = _zz_2[2];
  assign _zz_46 = {7'd0, _zz_45};
  assign _zz_47 = _zz_2[3];
  assign _zz_48 = {7'd0, _zz_47};
  assign _zz_49 = (_zz_50 + _zz_55);
  assign _zz_50 = (_zz_52 + _zz_54);
  assign _zz_51 = _zz_2[4];
  assign _zz_52 = {7'd0, _zz_51};
  assign _zz_53 = _zz_2[5];
  assign _zz_54 = {7'd0, _zz_53};
  assign _zz_55 = (_zz_57 + _zz_59);
  assign _zz_56 = _zz_2[6];
  assign _zz_57 = {7'd0, _zz_56};
  assign _zz_58 = _zz_2[7];
  assign _zz_59 = {7'd0, _zz_58};
  assign _zz_60 = (_zz_61 + _zz_72);
  assign _zz_61 = (_zz_62 + _zz_67);
  assign _zz_62 = (_zz_64 + _zz_66);
  assign _zz_63 = _zz_2[8];
  assign _zz_64 = {7'd0, _zz_63};
  assign _zz_65 = _zz_2[9];
  assign _zz_66 = {7'd0, _zz_65};
  assign _zz_67 = (_zz_69 + _zz_71);
  assign _zz_68 = _zz_2[10];
  assign _zz_69 = {7'd0, _zz_68};
  assign _zz_70 = _zz_2[11];
  assign _zz_71 = {7'd0, _zz_70};
  assign _zz_72 = (_zz_73 + _zz_78);
  assign _zz_73 = (_zz_75 + _zz_77);
  assign _zz_74 = _zz_2[12];
  assign _zz_75 = {7'd0, _zz_74};
  assign _zz_76 = _zz_2[13];
  assign _zz_77 = {7'd0, _zz_76};
  assign _zz_78 = (_zz_80 + _zz_82);
  assign _zz_79 = _zz_2[14];
  assign _zz_80 = {7'd0, _zz_79};
  assign _zz_81 = _zz_2[15];
  assign _zz_82 = {7'd0, _zz_81};
  assign _zz_83 = (_zz_84 + _zz_107);
  assign _zz_84 = (_zz_85 + _zz_96);
  assign _zz_85 = (_zz_86 + _zz_91);
  assign _zz_86 = (_zz_88 + _zz_90);
  assign _zz_87 = _zz_2[16];
  assign _zz_88 = {7'd0, _zz_87};
  assign _zz_89 = _zz_2[17];
  assign _zz_90 = {7'd0, _zz_89};
  assign _zz_91 = (_zz_93 + _zz_95);
  assign _zz_92 = _zz_2[18];
  assign _zz_93 = {7'd0, _zz_92};
  assign _zz_94 = _zz_2[19];
  assign _zz_95 = {7'd0, _zz_94};
  assign _zz_96 = (_zz_97 + _zz_102);
  assign _zz_97 = (_zz_99 + _zz_101);
  assign _zz_98 = _zz_2[20];
  assign _zz_99 = {7'd0, _zz_98};
  assign _zz_100 = _zz_2[21];
  assign _zz_101 = {7'd0, _zz_100};
  assign _zz_102 = (_zz_104 + _zz_106);
  assign _zz_103 = _zz_2[22];
  assign _zz_104 = {7'd0, _zz_103};
  assign _zz_105 = _zz_2[23];
  assign _zz_106 = {7'd0, _zz_105};
  assign _zz_107 = (_zz_108 + _zz_119);
  assign _zz_108 = (_zz_109 + _zz_114);
  assign _zz_109 = (_zz_111 + _zz_113);
  assign _zz_110 = _zz_2[24];
  assign _zz_111 = {7'd0, _zz_110};
  assign _zz_112 = _zz_2[25];
  assign _zz_113 = {7'd0, _zz_112};
  assign _zz_114 = (_zz_116 + _zz_118);
  assign _zz_115 = _zz_2[26];
  assign _zz_116 = {7'd0, _zz_115};
  assign _zz_117 = _zz_2[27];
  assign _zz_118 = {7'd0, _zz_117};
  assign _zz_119 = (_zz_120 + _zz_125);
  assign _zz_120 = (_zz_122 + _zz_124);
  assign _zz_121 = _zz_2[28];
  assign _zz_122 = {7'd0, _zz_121};
  assign _zz_123 = _zz_2[29];
  assign _zz_124 = {7'd0, _zz_123};
  assign _zz_125 = (_zz_127 + _zz_129);
  assign _zz_126 = _zz_2[30];
  assign _zz_127 = {7'd0, _zz_126};
  assign _zz_128 = _zz_2[31];
  assign _zz_129 = {7'd0, _zz_128};
  assign _zz_130 = (_zz_131 + _zz_178);
  assign _zz_131 = (_zz_132 + _zz_155);
  assign _zz_132 = (_zz_133 + _zz_144);
  assign _zz_133 = (_zz_134 + _zz_139);
  assign _zz_134 = (_zz_136 + _zz_138);
  assign _zz_135 = _zz_2[32];
  assign _zz_136 = {7'd0, _zz_135};
  assign _zz_137 = _zz_2[33];
  assign _zz_138 = {7'd0, _zz_137};
  assign _zz_139 = (_zz_141 + _zz_143);
  assign _zz_140 = _zz_2[34];
  assign _zz_141 = {7'd0, _zz_140};
  assign _zz_142 = _zz_2[35];
  assign _zz_143 = {7'd0, _zz_142};
  assign _zz_144 = (_zz_145 + _zz_150);
  assign _zz_145 = (_zz_147 + _zz_149);
  assign _zz_146 = _zz_2[36];
  assign _zz_147 = {7'd0, _zz_146};
  assign _zz_148 = _zz_2[37];
  assign _zz_149 = {7'd0, _zz_148};
  assign _zz_150 = (_zz_152 + _zz_154);
  assign _zz_151 = _zz_2[38];
  assign _zz_152 = {7'd0, _zz_151};
  assign _zz_153 = _zz_2[39];
  assign _zz_154 = {7'd0, _zz_153};
  assign _zz_155 = (_zz_156 + _zz_167);
  assign _zz_156 = (_zz_157 + _zz_162);
  assign _zz_157 = (_zz_159 + _zz_161);
  assign _zz_158 = _zz_2[40];
  assign _zz_159 = {7'd0, _zz_158};
  assign _zz_160 = _zz_2[41];
  assign _zz_161 = {7'd0, _zz_160};
  assign _zz_162 = (_zz_164 + _zz_166);
  assign _zz_163 = _zz_2[42];
  assign _zz_164 = {7'd0, _zz_163};
  assign _zz_165 = _zz_2[43];
  assign _zz_166 = {7'd0, _zz_165};
  assign _zz_167 = (_zz_168 + _zz_173);
  assign _zz_168 = (_zz_170 + _zz_172);
  assign _zz_169 = _zz_2[44];
  assign _zz_170 = {7'd0, _zz_169};
  assign _zz_171 = _zz_2[45];
  assign _zz_172 = {7'd0, _zz_171};
  assign _zz_173 = (_zz_175 + _zz_177);
  assign _zz_174 = _zz_2[46];
  assign _zz_175 = {7'd0, _zz_174};
  assign _zz_176 = _zz_2[47];
  assign _zz_177 = {7'd0, _zz_176};
  assign _zz_178 = (_zz_179 + _zz_202);
  assign _zz_179 = (_zz_180 + _zz_191);
  assign _zz_180 = (_zz_181 + _zz_186);
  assign _zz_181 = (_zz_183 + _zz_185);
  assign _zz_182 = _zz_2[48];
  assign _zz_183 = {7'd0, _zz_182};
  assign _zz_184 = _zz_2[49];
  assign _zz_185 = {7'd0, _zz_184};
  assign _zz_186 = (_zz_188 + _zz_190);
  assign _zz_187 = _zz_2[50];
  assign _zz_188 = {7'd0, _zz_187};
  assign _zz_189 = _zz_2[51];
  assign _zz_190 = {7'd0, _zz_189};
  assign _zz_191 = (_zz_192 + _zz_197);
  assign _zz_192 = (_zz_194 + _zz_196);
  assign _zz_193 = _zz_2[52];
  assign _zz_194 = {7'd0, _zz_193};
  assign _zz_195 = _zz_2[53];
  assign _zz_196 = {7'd0, _zz_195};
  assign _zz_197 = (_zz_199 + _zz_201);
  assign _zz_198 = _zz_2[54];
  assign _zz_199 = {7'd0, _zz_198};
  assign _zz_200 = _zz_2[55];
  assign _zz_201 = {7'd0, _zz_200};
  assign _zz_202 = (_zz_203 + _zz_214);
  assign _zz_203 = (_zz_204 + _zz_209);
  assign _zz_204 = (_zz_206 + _zz_208);
  assign _zz_205 = _zz_2[56];
  assign _zz_206 = {7'd0, _zz_205};
  assign _zz_207 = _zz_2[57];
  assign _zz_208 = {7'd0, _zz_207};
  assign _zz_209 = (_zz_211 + _zz_213);
  assign _zz_210 = _zz_2[58];
  assign _zz_211 = {7'd0, _zz_210};
  assign _zz_212 = _zz_2[59];
  assign _zz_213 = {7'd0, _zz_212};
  assign _zz_214 = (_zz_215 + _zz_220);
  assign _zz_215 = (_zz_217 + _zz_219);
  assign _zz_216 = _zz_2[60];
  assign _zz_217 = {7'd0, _zz_216};
  assign _zz_218 = _zz_2[61];
  assign _zz_219 = {7'd0, _zz_218};
  assign _zz_220 = (_zz_222 + _zz_224);
  assign _zz_221 = _zz_2[62];
  assign _zz_222 = {7'd0, _zz_221};
  assign _zz_223 = _zz_2[63];
  assign _zz_224 = {7'd0, _zz_223};
  assign _zz_225 = (_zz_226 + _zz_321);
  assign _zz_226 = (_zz_227 + _zz_274);
  assign _zz_227 = (_zz_228 + _zz_251);
  assign _zz_228 = (_zz_229 + _zz_240);
  assign _zz_229 = (_zz_230 + _zz_235);
  assign _zz_230 = (_zz_232 + _zz_234);
  assign _zz_231 = _zz_2[64];
  assign _zz_232 = {7'd0, _zz_231};
  assign _zz_233 = _zz_2[65];
  assign _zz_234 = {7'd0, _zz_233};
  assign _zz_235 = (_zz_237 + _zz_239);
  assign _zz_236 = _zz_2[66];
  assign _zz_237 = {7'd0, _zz_236};
  assign _zz_238 = _zz_2[67];
  assign _zz_239 = {7'd0, _zz_238};
  assign _zz_240 = (_zz_241 + _zz_246);
  assign _zz_241 = (_zz_243 + _zz_245);
  assign _zz_242 = _zz_2[68];
  assign _zz_243 = {7'd0, _zz_242};
  assign _zz_244 = _zz_2[69];
  assign _zz_245 = {7'd0, _zz_244};
  assign _zz_246 = (_zz_248 + _zz_250);
  assign _zz_247 = _zz_2[70];
  assign _zz_248 = {7'd0, _zz_247};
  assign _zz_249 = _zz_2[71];
  assign _zz_250 = {7'd0, _zz_249};
  assign _zz_251 = (_zz_252 + _zz_263);
  assign _zz_252 = (_zz_253 + _zz_258);
  assign _zz_253 = (_zz_255 + _zz_257);
  assign _zz_254 = _zz_2[72];
  assign _zz_255 = {7'd0, _zz_254};
  assign _zz_256 = _zz_2[73];
  assign _zz_257 = {7'd0, _zz_256};
  assign _zz_258 = (_zz_260 + _zz_262);
  assign _zz_259 = _zz_2[74];
  assign _zz_260 = {7'd0, _zz_259};
  assign _zz_261 = _zz_2[75];
  assign _zz_262 = {7'd0, _zz_261};
  assign _zz_263 = (_zz_264 + _zz_269);
  assign _zz_264 = (_zz_266 + _zz_268);
  assign _zz_265 = _zz_2[76];
  assign _zz_266 = {7'd0, _zz_265};
  assign _zz_267 = _zz_2[77];
  assign _zz_268 = {7'd0, _zz_267};
  assign _zz_269 = (_zz_271 + _zz_273);
  assign _zz_270 = _zz_2[78];
  assign _zz_271 = {7'd0, _zz_270};
  assign _zz_272 = _zz_2[79];
  assign _zz_273 = {7'd0, _zz_272};
  assign _zz_274 = (_zz_275 + _zz_298);
  assign _zz_275 = (_zz_276 + _zz_287);
  assign _zz_276 = (_zz_277 + _zz_282);
  assign _zz_277 = (_zz_279 + _zz_281);
  assign _zz_278 = _zz_2[80];
  assign _zz_279 = {7'd0, _zz_278};
  assign _zz_280 = _zz_2[81];
  assign _zz_281 = {7'd0, _zz_280};
  assign _zz_282 = (_zz_284 + _zz_286);
  assign _zz_283 = _zz_2[82];
  assign _zz_284 = {7'd0, _zz_283};
  assign _zz_285 = _zz_2[83];
  assign _zz_286 = {7'd0, _zz_285};
  assign _zz_287 = (_zz_288 + _zz_293);
  assign _zz_288 = (_zz_290 + _zz_292);
  assign _zz_289 = _zz_2[84];
  assign _zz_290 = {7'd0, _zz_289};
  assign _zz_291 = _zz_2[85];
  assign _zz_292 = {7'd0, _zz_291};
  assign _zz_293 = (_zz_295 + _zz_297);
  assign _zz_294 = _zz_2[86];
  assign _zz_295 = {7'd0, _zz_294};
  assign _zz_296 = _zz_2[87];
  assign _zz_297 = {7'd0, _zz_296};
  assign _zz_298 = (_zz_299 + _zz_310);
  assign _zz_299 = (_zz_300 + _zz_305);
  assign _zz_300 = (_zz_302 + _zz_304);
  assign _zz_301 = _zz_2[88];
  assign _zz_302 = {7'd0, _zz_301};
  assign _zz_303 = _zz_2[89];
  assign _zz_304 = {7'd0, _zz_303};
  assign _zz_305 = (_zz_307 + _zz_309);
  assign _zz_306 = _zz_2[90];
  assign _zz_307 = {7'd0, _zz_306};
  assign _zz_308 = _zz_2[91];
  assign _zz_309 = {7'd0, _zz_308};
  assign _zz_310 = (_zz_311 + _zz_316);
  assign _zz_311 = (_zz_313 + _zz_315);
  assign _zz_312 = _zz_2[92];
  assign _zz_313 = {7'd0, _zz_312};
  assign _zz_314 = _zz_2[93];
  assign _zz_315 = {7'd0, _zz_314};
  assign _zz_316 = (_zz_318 + _zz_320);
  assign _zz_317 = _zz_2[94];
  assign _zz_318 = {7'd0, _zz_317};
  assign _zz_319 = _zz_2[95];
  assign _zz_320 = {7'd0, _zz_319};
  assign _zz_321 = (_zz_322 + _zz_369);
  assign _zz_322 = (_zz_323 + _zz_346);
  assign _zz_323 = (_zz_324 + _zz_335);
  assign _zz_324 = (_zz_325 + _zz_330);
  assign _zz_325 = (_zz_327 + _zz_329);
  assign _zz_326 = _zz_2[96];
  assign _zz_327 = {7'd0, _zz_326};
  assign _zz_328 = _zz_2[97];
  assign _zz_329 = {7'd0, _zz_328};
  assign _zz_330 = (_zz_332 + _zz_334);
  assign _zz_331 = _zz_2[98];
  assign _zz_332 = {7'd0, _zz_331};
  assign _zz_333 = _zz_2[99];
  assign _zz_334 = {7'd0, _zz_333};
  assign _zz_335 = (_zz_336 + _zz_341);
  assign _zz_336 = (_zz_338 + _zz_340);
  assign _zz_337 = _zz_2[100];
  assign _zz_338 = {7'd0, _zz_337};
  assign _zz_339 = _zz_2[101];
  assign _zz_340 = {7'd0, _zz_339};
  assign _zz_341 = (_zz_343 + _zz_345);
  assign _zz_342 = _zz_2[102];
  assign _zz_343 = {7'd0, _zz_342};
  assign _zz_344 = _zz_2[103];
  assign _zz_345 = {7'd0, _zz_344};
  assign _zz_346 = (_zz_347 + _zz_358);
  assign _zz_347 = (_zz_348 + _zz_353);
  assign _zz_348 = (_zz_350 + _zz_352);
  assign _zz_349 = _zz_2[104];
  assign _zz_350 = {7'd0, _zz_349};
  assign _zz_351 = _zz_2[105];
  assign _zz_352 = {7'd0, _zz_351};
  assign _zz_353 = (_zz_355 + _zz_357);
  assign _zz_354 = _zz_2[106];
  assign _zz_355 = {7'd0, _zz_354};
  assign _zz_356 = _zz_2[107];
  assign _zz_357 = {7'd0, _zz_356};
  assign _zz_358 = (_zz_359 + _zz_364);
  assign _zz_359 = (_zz_361 + _zz_363);
  assign _zz_360 = _zz_2[108];
  assign _zz_361 = {7'd0, _zz_360};
  assign _zz_362 = _zz_2[109];
  assign _zz_363 = {7'd0, _zz_362};
  assign _zz_364 = (_zz_366 + _zz_368);
  assign _zz_365 = _zz_2[110];
  assign _zz_366 = {7'd0, _zz_365};
  assign _zz_367 = _zz_2[111];
  assign _zz_368 = {7'd0, _zz_367};
  assign _zz_369 = (_zz_370 + _zz_393);
  assign _zz_370 = (_zz_371 + _zz_382);
  assign _zz_371 = (_zz_372 + _zz_377);
  assign _zz_372 = (_zz_374 + _zz_376);
  assign _zz_373 = _zz_2[112];
  assign _zz_374 = {7'd0, _zz_373};
  assign _zz_375 = _zz_2[113];
  assign _zz_376 = {7'd0, _zz_375};
  assign _zz_377 = (_zz_379 + _zz_381);
  assign _zz_378 = _zz_2[114];
  assign _zz_379 = {7'd0, _zz_378};
  assign _zz_380 = _zz_2[115];
  assign _zz_381 = {7'd0, _zz_380};
  assign _zz_382 = (_zz_383 + _zz_388);
  assign _zz_383 = (_zz_385 + _zz_387);
  assign _zz_384 = _zz_2[116];
  assign _zz_385 = {7'd0, _zz_384};
  assign _zz_386 = _zz_2[117];
  assign _zz_387 = {7'd0, _zz_386};
  assign _zz_388 = (_zz_390 + _zz_392);
  assign _zz_389 = _zz_2[118];
  assign _zz_390 = {7'd0, _zz_389};
  assign _zz_391 = _zz_2[119];
  assign _zz_392 = {7'd0, _zz_391};
  assign _zz_393 = (_zz_394 + _zz_405);
  assign _zz_394 = (_zz_395 + _zz_400);
  assign _zz_395 = (_zz_397 + _zz_399);
  assign _zz_396 = _zz_2[120];
  assign _zz_397 = {7'd0, _zz_396};
  assign _zz_398 = _zz_2[121];
  assign _zz_399 = {7'd0, _zz_398};
  assign _zz_400 = (_zz_402 + _zz_404);
  assign _zz_401 = _zz_2[122];
  assign _zz_402 = {7'd0, _zz_401};
  assign _zz_403 = _zz_2[123];
  assign _zz_404 = {7'd0, _zz_403};
  assign _zz_405 = (_zz_406 + _zz_411);
  assign _zz_406 = (_zz_408 + _zz_410);
  assign _zz_407 = _zz_2[124];
  assign _zz_408 = {7'd0, _zz_407};
  assign _zz_409 = _zz_2[125];
  assign _zz_410 = {7'd0, _zz_409};
  assign _zz_411 = (_zz_413 + _zz_415);
  assign _zz_412 = _zz_2[126];
  assign _zz_413 = {7'd0, _zz_412};
  assign _zz_414 = _zz_2[127];
  assign _zz_415 = {7'd0, _zz_414};
  assign _zz_416 = _zz_22;
  assign _zz_417 = _zz_25;
  assign _zz_418 = fixTo_3_dout;
  assign _zz_419 = 32'hffffffff;
  assign _zz_420 = 32'hffffffff;
  assign _zz_421 = fixTo_1_dout;
  assign _zz_422 = {fixTo_4_dout,_zz_423};
  assign _zz_423 = 32'h0;
  assign _zz_424 = _zz_3;
  assign _zz_425 = fixTo_6_dout;
  assign _zz_426 = fixTo_2_dout;
  assign _zz_427 = {fixTo_7_dout,_zz_428};
  assign _zz_428 = 32'h0;
  assign _zz_429 = _zz_3;
  assign _zz_430 = fixTo_9_dout;
  assign _zz_431 = ($signed(k_real) + $signed(k_imag));
  assign _zz_432 = ($signed(_zz_4) - $signed(_zz_433));
  assign _zz_433 = ($signed(_zz_434) * $signed(k_imag));
  assign _zz_434 = ($signed(deltax_real) + $signed(deltax_imag));
  assign _zz_435 = ($signed(_zz_4) + $signed(_zz_436));
  assign _zz_436 = ($signed(_zz_437) * $signed(k_real));
  assign _zz_437 = ($signed(deltax_imag) - $signed(deltax_real));
  assign _zz_438 = _zz_439[63 : 0];
  assign _zz_439 = fixTo_10_dout;
  assign _zz_440 = ({16'd0,y1_real} <<< 16);
  assign _zz_441 = {{16{_zz_440[47]}}, _zz_440};
  assign _zz_442 = _zz_443[63 : 0];
  assign _zz_443 = fixTo_11_dout;
  assign _zz_444 = ({16'd0,y1_imag} <<< 16);
  assign _zz_445 = {{16{_zz_444[47]}}, _zz_444};
  assign _zz_446 = fixTo_12_dout;
  assign _zz_447 = fixTo_13_dout;
  SInt32fixTo31_0_ROUNDTOINF fixTo (
    .din     (io_data_in[31:0]  ), //i
    .dout    (fixTo_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_1 (
    .din     (_zz_9[31:0]         ), //i
    .dout    (fixTo_1_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_2 (
    .din     (_zz_10[31:0]        ), //i
    .dout    (fixTo_2_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_3 (
    .din     (_zz_11[31:0]        ), //i
    .dout    (fixTo_3_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_4 (
    .din     (_zz_12[31:0]        ), //i
    .dout    (fixTo_4_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_5 (
    .din     (_zz_13[31:0]        ), //i
    .dout    (fixTo_5_dout[31:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_6 (
    .din     (_zz_14[63:0]        ), //i
    .dout    (fixTo_6_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_7 (
    .din     (_zz_15[31:0]        ), //i
    .dout    (fixTo_7_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_8 (
    .din     (_zz_16[31:0]        ), //i
    .dout    (fixTo_8_dout[31:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_9 (
    .din     (_zz_17[63:0]        ), //i
    .dout    (fixTo_9_dout[31:0]  )  //o
  );
  SInt64fixTo63_0_ROUNDTOINF fixTo_10 (
    .din     (_zz_18[63:0]         ), //i
    .dout    (fixTo_10_dout[63:0]  )  //o
  );
  SInt64fixTo63_0_ROUNDTOINF fixTo_11 (
    .din     (_zz_19[63:0]         ), //i
    .dout    (fixTo_11_dout[63:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_12 (
    .din     (_zz_20[63:0]         ), //i
    .dout    (fixTo_12_dout[31:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_13 (
    .din     (_zz_21[63:0]         ), //i
    .dout    (fixTo_13_dout[31:0]  )  //o
  );
  always @(*) begin
    case(position)
      7'b0000000 : begin
        _zz_22 = pindx_tb_0;
        _zz_23 = pfunc_tb_0_real;
        _zz_24 = pfunc_tb_0_imag;
      end
      7'b0000001 : begin
        _zz_22 = pindx_tb_1;
        _zz_23 = pfunc_tb_1_real;
        _zz_24 = pfunc_tb_1_imag;
      end
      7'b0000010 : begin
        _zz_22 = pindx_tb_2;
        _zz_23 = pfunc_tb_2_real;
        _zz_24 = pfunc_tb_2_imag;
      end
      7'b0000011 : begin
        _zz_22 = pindx_tb_3;
        _zz_23 = pfunc_tb_3_real;
        _zz_24 = pfunc_tb_3_imag;
      end
      7'b0000100 : begin
        _zz_22 = pindx_tb_4;
        _zz_23 = pfunc_tb_4_real;
        _zz_24 = pfunc_tb_4_imag;
      end
      7'b0000101 : begin
        _zz_22 = pindx_tb_5;
        _zz_23 = pfunc_tb_5_real;
        _zz_24 = pfunc_tb_5_imag;
      end
      7'b0000110 : begin
        _zz_22 = pindx_tb_6;
        _zz_23 = pfunc_tb_6_real;
        _zz_24 = pfunc_tb_6_imag;
      end
      7'b0000111 : begin
        _zz_22 = pindx_tb_7;
        _zz_23 = pfunc_tb_7_real;
        _zz_24 = pfunc_tb_7_imag;
      end
      7'b0001000 : begin
        _zz_22 = pindx_tb_8;
        _zz_23 = pfunc_tb_8_real;
        _zz_24 = pfunc_tb_8_imag;
      end
      7'b0001001 : begin
        _zz_22 = pindx_tb_9;
        _zz_23 = pfunc_tb_9_real;
        _zz_24 = pfunc_tb_9_imag;
      end
      7'b0001010 : begin
        _zz_22 = pindx_tb_10;
        _zz_23 = pfunc_tb_10_real;
        _zz_24 = pfunc_tb_10_imag;
      end
      7'b0001011 : begin
        _zz_22 = pindx_tb_11;
        _zz_23 = pfunc_tb_11_real;
        _zz_24 = pfunc_tb_11_imag;
      end
      7'b0001100 : begin
        _zz_22 = pindx_tb_12;
        _zz_23 = pfunc_tb_12_real;
        _zz_24 = pfunc_tb_12_imag;
      end
      7'b0001101 : begin
        _zz_22 = pindx_tb_13;
        _zz_23 = pfunc_tb_13_real;
        _zz_24 = pfunc_tb_13_imag;
      end
      7'b0001110 : begin
        _zz_22 = pindx_tb_14;
        _zz_23 = pfunc_tb_14_real;
        _zz_24 = pfunc_tb_14_imag;
      end
      7'b0001111 : begin
        _zz_22 = pindx_tb_15;
        _zz_23 = pfunc_tb_15_real;
        _zz_24 = pfunc_tb_15_imag;
      end
      7'b0010000 : begin
        _zz_22 = pindx_tb_16;
        _zz_23 = pfunc_tb_16_real;
        _zz_24 = pfunc_tb_16_imag;
      end
      7'b0010001 : begin
        _zz_22 = pindx_tb_17;
        _zz_23 = pfunc_tb_17_real;
        _zz_24 = pfunc_tb_17_imag;
      end
      7'b0010010 : begin
        _zz_22 = pindx_tb_18;
        _zz_23 = pfunc_tb_18_real;
        _zz_24 = pfunc_tb_18_imag;
      end
      7'b0010011 : begin
        _zz_22 = pindx_tb_19;
        _zz_23 = pfunc_tb_19_real;
        _zz_24 = pfunc_tb_19_imag;
      end
      7'b0010100 : begin
        _zz_22 = pindx_tb_20;
        _zz_23 = pfunc_tb_20_real;
        _zz_24 = pfunc_tb_20_imag;
      end
      7'b0010101 : begin
        _zz_22 = pindx_tb_21;
        _zz_23 = pfunc_tb_21_real;
        _zz_24 = pfunc_tb_21_imag;
      end
      7'b0010110 : begin
        _zz_22 = pindx_tb_22;
        _zz_23 = pfunc_tb_22_real;
        _zz_24 = pfunc_tb_22_imag;
      end
      7'b0010111 : begin
        _zz_22 = pindx_tb_23;
        _zz_23 = pfunc_tb_23_real;
        _zz_24 = pfunc_tb_23_imag;
      end
      7'b0011000 : begin
        _zz_22 = pindx_tb_24;
        _zz_23 = pfunc_tb_24_real;
        _zz_24 = pfunc_tb_24_imag;
      end
      7'b0011001 : begin
        _zz_22 = pindx_tb_25;
        _zz_23 = pfunc_tb_25_real;
        _zz_24 = pfunc_tb_25_imag;
      end
      7'b0011010 : begin
        _zz_22 = pindx_tb_26;
        _zz_23 = pfunc_tb_26_real;
        _zz_24 = pfunc_tb_26_imag;
      end
      7'b0011011 : begin
        _zz_22 = pindx_tb_27;
        _zz_23 = pfunc_tb_27_real;
        _zz_24 = pfunc_tb_27_imag;
      end
      7'b0011100 : begin
        _zz_22 = pindx_tb_28;
        _zz_23 = pfunc_tb_28_real;
        _zz_24 = pfunc_tb_28_imag;
      end
      7'b0011101 : begin
        _zz_22 = pindx_tb_29;
        _zz_23 = pfunc_tb_29_real;
        _zz_24 = pfunc_tb_29_imag;
      end
      7'b0011110 : begin
        _zz_22 = pindx_tb_30;
        _zz_23 = pfunc_tb_30_real;
        _zz_24 = pfunc_tb_30_imag;
      end
      7'b0011111 : begin
        _zz_22 = pindx_tb_31;
        _zz_23 = pfunc_tb_31_real;
        _zz_24 = pfunc_tb_31_imag;
      end
      7'b0100000 : begin
        _zz_22 = pindx_tb_32;
        _zz_23 = pfunc_tb_32_real;
        _zz_24 = pfunc_tb_32_imag;
      end
      7'b0100001 : begin
        _zz_22 = pindx_tb_33;
        _zz_23 = pfunc_tb_33_real;
        _zz_24 = pfunc_tb_33_imag;
      end
      7'b0100010 : begin
        _zz_22 = pindx_tb_34;
        _zz_23 = pfunc_tb_34_real;
        _zz_24 = pfunc_tb_34_imag;
      end
      7'b0100011 : begin
        _zz_22 = pindx_tb_35;
        _zz_23 = pfunc_tb_35_real;
        _zz_24 = pfunc_tb_35_imag;
      end
      7'b0100100 : begin
        _zz_22 = pindx_tb_36;
        _zz_23 = pfunc_tb_36_real;
        _zz_24 = pfunc_tb_36_imag;
      end
      7'b0100101 : begin
        _zz_22 = pindx_tb_37;
        _zz_23 = pfunc_tb_37_real;
        _zz_24 = pfunc_tb_37_imag;
      end
      7'b0100110 : begin
        _zz_22 = pindx_tb_38;
        _zz_23 = pfunc_tb_38_real;
        _zz_24 = pfunc_tb_38_imag;
      end
      7'b0100111 : begin
        _zz_22 = pindx_tb_39;
        _zz_23 = pfunc_tb_39_real;
        _zz_24 = pfunc_tb_39_imag;
      end
      7'b0101000 : begin
        _zz_22 = pindx_tb_40;
        _zz_23 = pfunc_tb_40_real;
        _zz_24 = pfunc_tb_40_imag;
      end
      7'b0101001 : begin
        _zz_22 = pindx_tb_41;
        _zz_23 = pfunc_tb_41_real;
        _zz_24 = pfunc_tb_41_imag;
      end
      7'b0101010 : begin
        _zz_22 = pindx_tb_42;
        _zz_23 = pfunc_tb_42_real;
        _zz_24 = pfunc_tb_42_imag;
      end
      7'b0101011 : begin
        _zz_22 = pindx_tb_43;
        _zz_23 = pfunc_tb_43_real;
        _zz_24 = pfunc_tb_43_imag;
      end
      7'b0101100 : begin
        _zz_22 = pindx_tb_44;
        _zz_23 = pfunc_tb_44_real;
        _zz_24 = pfunc_tb_44_imag;
      end
      7'b0101101 : begin
        _zz_22 = pindx_tb_45;
        _zz_23 = pfunc_tb_45_real;
        _zz_24 = pfunc_tb_45_imag;
      end
      7'b0101110 : begin
        _zz_22 = pindx_tb_46;
        _zz_23 = pfunc_tb_46_real;
        _zz_24 = pfunc_tb_46_imag;
      end
      7'b0101111 : begin
        _zz_22 = pindx_tb_47;
        _zz_23 = pfunc_tb_47_real;
        _zz_24 = pfunc_tb_47_imag;
      end
      7'b0110000 : begin
        _zz_22 = pindx_tb_48;
        _zz_23 = pfunc_tb_48_real;
        _zz_24 = pfunc_tb_48_imag;
      end
      7'b0110001 : begin
        _zz_22 = pindx_tb_49;
        _zz_23 = pfunc_tb_49_real;
        _zz_24 = pfunc_tb_49_imag;
      end
      7'b0110010 : begin
        _zz_22 = pindx_tb_50;
        _zz_23 = pfunc_tb_50_real;
        _zz_24 = pfunc_tb_50_imag;
      end
      7'b0110011 : begin
        _zz_22 = pindx_tb_51;
        _zz_23 = pfunc_tb_51_real;
        _zz_24 = pfunc_tb_51_imag;
      end
      7'b0110100 : begin
        _zz_22 = pindx_tb_52;
        _zz_23 = pfunc_tb_52_real;
        _zz_24 = pfunc_tb_52_imag;
      end
      7'b0110101 : begin
        _zz_22 = pindx_tb_53;
        _zz_23 = pfunc_tb_53_real;
        _zz_24 = pfunc_tb_53_imag;
      end
      7'b0110110 : begin
        _zz_22 = pindx_tb_54;
        _zz_23 = pfunc_tb_54_real;
        _zz_24 = pfunc_tb_54_imag;
      end
      7'b0110111 : begin
        _zz_22 = pindx_tb_55;
        _zz_23 = pfunc_tb_55_real;
        _zz_24 = pfunc_tb_55_imag;
      end
      7'b0111000 : begin
        _zz_22 = pindx_tb_56;
        _zz_23 = pfunc_tb_56_real;
        _zz_24 = pfunc_tb_56_imag;
      end
      7'b0111001 : begin
        _zz_22 = pindx_tb_57;
        _zz_23 = pfunc_tb_57_real;
        _zz_24 = pfunc_tb_57_imag;
      end
      7'b0111010 : begin
        _zz_22 = pindx_tb_58;
        _zz_23 = pfunc_tb_58_real;
        _zz_24 = pfunc_tb_58_imag;
      end
      7'b0111011 : begin
        _zz_22 = pindx_tb_59;
        _zz_23 = pfunc_tb_59_real;
        _zz_24 = pfunc_tb_59_imag;
      end
      7'b0111100 : begin
        _zz_22 = pindx_tb_60;
        _zz_23 = pfunc_tb_60_real;
        _zz_24 = pfunc_tb_60_imag;
      end
      7'b0111101 : begin
        _zz_22 = pindx_tb_61;
        _zz_23 = pfunc_tb_61_real;
        _zz_24 = pfunc_tb_61_imag;
      end
      7'b0111110 : begin
        _zz_22 = pindx_tb_62;
        _zz_23 = pfunc_tb_62_real;
        _zz_24 = pfunc_tb_62_imag;
      end
      7'b0111111 : begin
        _zz_22 = pindx_tb_63;
        _zz_23 = pfunc_tb_63_real;
        _zz_24 = pfunc_tb_63_imag;
      end
      7'b1000000 : begin
        _zz_22 = pindx_tb_64;
        _zz_23 = pfunc_tb_64_real;
        _zz_24 = pfunc_tb_64_imag;
      end
      7'b1000001 : begin
        _zz_22 = pindx_tb_65;
        _zz_23 = pfunc_tb_65_real;
        _zz_24 = pfunc_tb_65_imag;
      end
      7'b1000010 : begin
        _zz_22 = pindx_tb_66;
        _zz_23 = pfunc_tb_66_real;
        _zz_24 = pfunc_tb_66_imag;
      end
      7'b1000011 : begin
        _zz_22 = pindx_tb_67;
        _zz_23 = pfunc_tb_67_real;
        _zz_24 = pfunc_tb_67_imag;
      end
      7'b1000100 : begin
        _zz_22 = pindx_tb_68;
        _zz_23 = pfunc_tb_68_real;
        _zz_24 = pfunc_tb_68_imag;
      end
      7'b1000101 : begin
        _zz_22 = pindx_tb_69;
        _zz_23 = pfunc_tb_69_real;
        _zz_24 = pfunc_tb_69_imag;
      end
      7'b1000110 : begin
        _zz_22 = pindx_tb_70;
        _zz_23 = pfunc_tb_70_real;
        _zz_24 = pfunc_tb_70_imag;
      end
      7'b1000111 : begin
        _zz_22 = pindx_tb_71;
        _zz_23 = pfunc_tb_71_real;
        _zz_24 = pfunc_tb_71_imag;
      end
      7'b1001000 : begin
        _zz_22 = pindx_tb_72;
        _zz_23 = pfunc_tb_72_real;
        _zz_24 = pfunc_tb_72_imag;
      end
      7'b1001001 : begin
        _zz_22 = pindx_tb_73;
        _zz_23 = pfunc_tb_73_real;
        _zz_24 = pfunc_tb_73_imag;
      end
      7'b1001010 : begin
        _zz_22 = pindx_tb_74;
        _zz_23 = pfunc_tb_74_real;
        _zz_24 = pfunc_tb_74_imag;
      end
      7'b1001011 : begin
        _zz_22 = pindx_tb_75;
        _zz_23 = pfunc_tb_75_real;
        _zz_24 = pfunc_tb_75_imag;
      end
      7'b1001100 : begin
        _zz_22 = pindx_tb_76;
        _zz_23 = pfunc_tb_76_real;
        _zz_24 = pfunc_tb_76_imag;
      end
      7'b1001101 : begin
        _zz_22 = pindx_tb_77;
        _zz_23 = pfunc_tb_77_real;
        _zz_24 = pfunc_tb_77_imag;
      end
      7'b1001110 : begin
        _zz_22 = pindx_tb_78;
        _zz_23 = pfunc_tb_78_real;
        _zz_24 = pfunc_tb_78_imag;
      end
      7'b1001111 : begin
        _zz_22 = pindx_tb_79;
        _zz_23 = pfunc_tb_79_real;
        _zz_24 = pfunc_tb_79_imag;
      end
      7'b1010000 : begin
        _zz_22 = pindx_tb_80;
        _zz_23 = pfunc_tb_80_real;
        _zz_24 = pfunc_tb_80_imag;
      end
      7'b1010001 : begin
        _zz_22 = pindx_tb_81;
        _zz_23 = pfunc_tb_81_real;
        _zz_24 = pfunc_tb_81_imag;
      end
      7'b1010010 : begin
        _zz_22 = pindx_tb_82;
        _zz_23 = pfunc_tb_82_real;
        _zz_24 = pfunc_tb_82_imag;
      end
      7'b1010011 : begin
        _zz_22 = pindx_tb_83;
        _zz_23 = pfunc_tb_83_real;
        _zz_24 = pfunc_tb_83_imag;
      end
      7'b1010100 : begin
        _zz_22 = pindx_tb_84;
        _zz_23 = pfunc_tb_84_real;
        _zz_24 = pfunc_tb_84_imag;
      end
      7'b1010101 : begin
        _zz_22 = pindx_tb_85;
        _zz_23 = pfunc_tb_85_real;
        _zz_24 = pfunc_tb_85_imag;
      end
      7'b1010110 : begin
        _zz_22 = pindx_tb_86;
        _zz_23 = pfunc_tb_86_real;
        _zz_24 = pfunc_tb_86_imag;
      end
      7'b1010111 : begin
        _zz_22 = pindx_tb_87;
        _zz_23 = pfunc_tb_87_real;
        _zz_24 = pfunc_tb_87_imag;
      end
      7'b1011000 : begin
        _zz_22 = pindx_tb_88;
        _zz_23 = pfunc_tb_88_real;
        _zz_24 = pfunc_tb_88_imag;
      end
      7'b1011001 : begin
        _zz_22 = pindx_tb_89;
        _zz_23 = pfunc_tb_89_real;
        _zz_24 = pfunc_tb_89_imag;
      end
      7'b1011010 : begin
        _zz_22 = pindx_tb_90;
        _zz_23 = pfunc_tb_90_real;
        _zz_24 = pfunc_tb_90_imag;
      end
      7'b1011011 : begin
        _zz_22 = pindx_tb_91;
        _zz_23 = pfunc_tb_91_real;
        _zz_24 = pfunc_tb_91_imag;
      end
      7'b1011100 : begin
        _zz_22 = pindx_tb_92;
        _zz_23 = pfunc_tb_92_real;
        _zz_24 = pfunc_tb_92_imag;
      end
      7'b1011101 : begin
        _zz_22 = pindx_tb_93;
        _zz_23 = pfunc_tb_93_real;
        _zz_24 = pfunc_tb_93_imag;
      end
      7'b1011110 : begin
        _zz_22 = pindx_tb_94;
        _zz_23 = pfunc_tb_94_real;
        _zz_24 = pfunc_tb_94_imag;
      end
      7'b1011111 : begin
        _zz_22 = pindx_tb_95;
        _zz_23 = pfunc_tb_95_real;
        _zz_24 = pfunc_tb_95_imag;
      end
      7'b1100000 : begin
        _zz_22 = pindx_tb_96;
        _zz_23 = pfunc_tb_96_real;
        _zz_24 = pfunc_tb_96_imag;
      end
      7'b1100001 : begin
        _zz_22 = pindx_tb_97;
        _zz_23 = pfunc_tb_97_real;
        _zz_24 = pfunc_tb_97_imag;
      end
      7'b1100010 : begin
        _zz_22 = pindx_tb_98;
        _zz_23 = pfunc_tb_98_real;
        _zz_24 = pfunc_tb_98_imag;
      end
      7'b1100011 : begin
        _zz_22 = pindx_tb_99;
        _zz_23 = pfunc_tb_99_real;
        _zz_24 = pfunc_tb_99_imag;
      end
      7'b1100100 : begin
        _zz_22 = pindx_tb_100;
        _zz_23 = pfunc_tb_100_real;
        _zz_24 = pfunc_tb_100_imag;
      end
      7'b1100101 : begin
        _zz_22 = pindx_tb_101;
        _zz_23 = pfunc_tb_101_real;
        _zz_24 = pfunc_tb_101_imag;
      end
      7'b1100110 : begin
        _zz_22 = pindx_tb_102;
        _zz_23 = pfunc_tb_102_real;
        _zz_24 = pfunc_tb_102_imag;
      end
      7'b1100111 : begin
        _zz_22 = pindx_tb_103;
        _zz_23 = pfunc_tb_103_real;
        _zz_24 = pfunc_tb_103_imag;
      end
      7'b1101000 : begin
        _zz_22 = pindx_tb_104;
        _zz_23 = pfunc_tb_104_real;
        _zz_24 = pfunc_tb_104_imag;
      end
      7'b1101001 : begin
        _zz_22 = pindx_tb_105;
        _zz_23 = pfunc_tb_105_real;
        _zz_24 = pfunc_tb_105_imag;
      end
      7'b1101010 : begin
        _zz_22 = pindx_tb_106;
        _zz_23 = pfunc_tb_106_real;
        _zz_24 = pfunc_tb_106_imag;
      end
      7'b1101011 : begin
        _zz_22 = pindx_tb_107;
        _zz_23 = pfunc_tb_107_real;
        _zz_24 = pfunc_tb_107_imag;
      end
      7'b1101100 : begin
        _zz_22 = pindx_tb_108;
        _zz_23 = pfunc_tb_108_real;
        _zz_24 = pfunc_tb_108_imag;
      end
      7'b1101101 : begin
        _zz_22 = pindx_tb_109;
        _zz_23 = pfunc_tb_109_real;
        _zz_24 = pfunc_tb_109_imag;
      end
      7'b1101110 : begin
        _zz_22 = pindx_tb_110;
        _zz_23 = pfunc_tb_110_real;
        _zz_24 = pfunc_tb_110_imag;
      end
      7'b1101111 : begin
        _zz_22 = pindx_tb_111;
        _zz_23 = pfunc_tb_111_real;
        _zz_24 = pfunc_tb_111_imag;
      end
      7'b1110000 : begin
        _zz_22 = pindx_tb_112;
        _zz_23 = pfunc_tb_112_real;
        _zz_24 = pfunc_tb_112_imag;
      end
      7'b1110001 : begin
        _zz_22 = pindx_tb_113;
        _zz_23 = pfunc_tb_113_real;
        _zz_24 = pfunc_tb_113_imag;
      end
      7'b1110010 : begin
        _zz_22 = pindx_tb_114;
        _zz_23 = pfunc_tb_114_real;
        _zz_24 = pfunc_tb_114_imag;
      end
      7'b1110011 : begin
        _zz_22 = pindx_tb_115;
        _zz_23 = pfunc_tb_115_real;
        _zz_24 = pfunc_tb_115_imag;
      end
      7'b1110100 : begin
        _zz_22 = pindx_tb_116;
        _zz_23 = pfunc_tb_116_real;
        _zz_24 = pfunc_tb_116_imag;
      end
      7'b1110101 : begin
        _zz_22 = pindx_tb_117;
        _zz_23 = pfunc_tb_117_real;
        _zz_24 = pfunc_tb_117_imag;
      end
      7'b1110110 : begin
        _zz_22 = pindx_tb_118;
        _zz_23 = pfunc_tb_118_real;
        _zz_24 = pfunc_tb_118_imag;
      end
      7'b1110111 : begin
        _zz_22 = pindx_tb_119;
        _zz_23 = pfunc_tb_119_real;
        _zz_24 = pfunc_tb_119_imag;
      end
      7'b1111000 : begin
        _zz_22 = pindx_tb_120;
        _zz_23 = pfunc_tb_120_real;
        _zz_24 = pfunc_tb_120_imag;
      end
      7'b1111001 : begin
        _zz_22 = pindx_tb_121;
        _zz_23 = pfunc_tb_121_real;
        _zz_24 = pfunc_tb_121_imag;
      end
      7'b1111010 : begin
        _zz_22 = pindx_tb_122;
        _zz_23 = pfunc_tb_122_real;
        _zz_24 = pfunc_tb_122_imag;
      end
      7'b1111011 : begin
        _zz_22 = pindx_tb_123;
        _zz_23 = pfunc_tb_123_real;
        _zz_24 = pfunc_tb_123_imag;
      end
      7'b1111100 : begin
        _zz_22 = pindx_tb_124;
        _zz_23 = pfunc_tb_124_real;
        _zz_24 = pfunc_tb_124_imag;
      end
      7'b1111101 : begin
        _zz_22 = pindx_tb_125;
        _zz_23 = pfunc_tb_125_real;
        _zz_24 = pfunc_tb_125_imag;
      end
      7'b1111110 : begin
        _zz_22 = pindx_tb_126;
        _zz_23 = pfunc_tb_126_real;
        _zz_24 = pfunc_tb_126_imag;
      end
      default : begin
        _zz_22 = pindx_tb_127;
        _zz_23 = pfunc_tb_127_real;
        _zz_24 = pfunc_tb_127_imag;
      end
    endcase
  end

  always @(*) begin
    case(lzc)
      7'b0000000 : begin
        _zz_25 = pindx_tb_0;
        _zz_26 = pfunc_tb_0_real;
        _zz_27 = pfunc_tb_0_imag;
      end
      7'b0000001 : begin
        _zz_25 = pindx_tb_1;
        _zz_26 = pfunc_tb_1_real;
        _zz_27 = pfunc_tb_1_imag;
      end
      7'b0000010 : begin
        _zz_25 = pindx_tb_2;
        _zz_26 = pfunc_tb_2_real;
        _zz_27 = pfunc_tb_2_imag;
      end
      7'b0000011 : begin
        _zz_25 = pindx_tb_3;
        _zz_26 = pfunc_tb_3_real;
        _zz_27 = pfunc_tb_3_imag;
      end
      7'b0000100 : begin
        _zz_25 = pindx_tb_4;
        _zz_26 = pfunc_tb_4_real;
        _zz_27 = pfunc_tb_4_imag;
      end
      7'b0000101 : begin
        _zz_25 = pindx_tb_5;
        _zz_26 = pfunc_tb_5_real;
        _zz_27 = pfunc_tb_5_imag;
      end
      7'b0000110 : begin
        _zz_25 = pindx_tb_6;
        _zz_26 = pfunc_tb_6_real;
        _zz_27 = pfunc_tb_6_imag;
      end
      7'b0000111 : begin
        _zz_25 = pindx_tb_7;
        _zz_26 = pfunc_tb_7_real;
        _zz_27 = pfunc_tb_7_imag;
      end
      7'b0001000 : begin
        _zz_25 = pindx_tb_8;
        _zz_26 = pfunc_tb_8_real;
        _zz_27 = pfunc_tb_8_imag;
      end
      7'b0001001 : begin
        _zz_25 = pindx_tb_9;
        _zz_26 = pfunc_tb_9_real;
        _zz_27 = pfunc_tb_9_imag;
      end
      7'b0001010 : begin
        _zz_25 = pindx_tb_10;
        _zz_26 = pfunc_tb_10_real;
        _zz_27 = pfunc_tb_10_imag;
      end
      7'b0001011 : begin
        _zz_25 = pindx_tb_11;
        _zz_26 = pfunc_tb_11_real;
        _zz_27 = pfunc_tb_11_imag;
      end
      7'b0001100 : begin
        _zz_25 = pindx_tb_12;
        _zz_26 = pfunc_tb_12_real;
        _zz_27 = pfunc_tb_12_imag;
      end
      7'b0001101 : begin
        _zz_25 = pindx_tb_13;
        _zz_26 = pfunc_tb_13_real;
        _zz_27 = pfunc_tb_13_imag;
      end
      7'b0001110 : begin
        _zz_25 = pindx_tb_14;
        _zz_26 = pfunc_tb_14_real;
        _zz_27 = pfunc_tb_14_imag;
      end
      7'b0001111 : begin
        _zz_25 = pindx_tb_15;
        _zz_26 = pfunc_tb_15_real;
        _zz_27 = pfunc_tb_15_imag;
      end
      7'b0010000 : begin
        _zz_25 = pindx_tb_16;
        _zz_26 = pfunc_tb_16_real;
        _zz_27 = pfunc_tb_16_imag;
      end
      7'b0010001 : begin
        _zz_25 = pindx_tb_17;
        _zz_26 = pfunc_tb_17_real;
        _zz_27 = pfunc_tb_17_imag;
      end
      7'b0010010 : begin
        _zz_25 = pindx_tb_18;
        _zz_26 = pfunc_tb_18_real;
        _zz_27 = pfunc_tb_18_imag;
      end
      7'b0010011 : begin
        _zz_25 = pindx_tb_19;
        _zz_26 = pfunc_tb_19_real;
        _zz_27 = pfunc_tb_19_imag;
      end
      7'b0010100 : begin
        _zz_25 = pindx_tb_20;
        _zz_26 = pfunc_tb_20_real;
        _zz_27 = pfunc_tb_20_imag;
      end
      7'b0010101 : begin
        _zz_25 = pindx_tb_21;
        _zz_26 = pfunc_tb_21_real;
        _zz_27 = pfunc_tb_21_imag;
      end
      7'b0010110 : begin
        _zz_25 = pindx_tb_22;
        _zz_26 = pfunc_tb_22_real;
        _zz_27 = pfunc_tb_22_imag;
      end
      7'b0010111 : begin
        _zz_25 = pindx_tb_23;
        _zz_26 = pfunc_tb_23_real;
        _zz_27 = pfunc_tb_23_imag;
      end
      7'b0011000 : begin
        _zz_25 = pindx_tb_24;
        _zz_26 = pfunc_tb_24_real;
        _zz_27 = pfunc_tb_24_imag;
      end
      7'b0011001 : begin
        _zz_25 = pindx_tb_25;
        _zz_26 = pfunc_tb_25_real;
        _zz_27 = pfunc_tb_25_imag;
      end
      7'b0011010 : begin
        _zz_25 = pindx_tb_26;
        _zz_26 = pfunc_tb_26_real;
        _zz_27 = pfunc_tb_26_imag;
      end
      7'b0011011 : begin
        _zz_25 = pindx_tb_27;
        _zz_26 = pfunc_tb_27_real;
        _zz_27 = pfunc_tb_27_imag;
      end
      7'b0011100 : begin
        _zz_25 = pindx_tb_28;
        _zz_26 = pfunc_tb_28_real;
        _zz_27 = pfunc_tb_28_imag;
      end
      7'b0011101 : begin
        _zz_25 = pindx_tb_29;
        _zz_26 = pfunc_tb_29_real;
        _zz_27 = pfunc_tb_29_imag;
      end
      7'b0011110 : begin
        _zz_25 = pindx_tb_30;
        _zz_26 = pfunc_tb_30_real;
        _zz_27 = pfunc_tb_30_imag;
      end
      7'b0011111 : begin
        _zz_25 = pindx_tb_31;
        _zz_26 = pfunc_tb_31_real;
        _zz_27 = pfunc_tb_31_imag;
      end
      7'b0100000 : begin
        _zz_25 = pindx_tb_32;
        _zz_26 = pfunc_tb_32_real;
        _zz_27 = pfunc_tb_32_imag;
      end
      7'b0100001 : begin
        _zz_25 = pindx_tb_33;
        _zz_26 = pfunc_tb_33_real;
        _zz_27 = pfunc_tb_33_imag;
      end
      7'b0100010 : begin
        _zz_25 = pindx_tb_34;
        _zz_26 = pfunc_tb_34_real;
        _zz_27 = pfunc_tb_34_imag;
      end
      7'b0100011 : begin
        _zz_25 = pindx_tb_35;
        _zz_26 = pfunc_tb_35_real;
        _zz_27 = pfunc_tb_35_imag;
      end
      7'b0100100 : begin
        _zz_25 = pindx_tb_36;
        _zz_26 = pfunc_tb_36_real;
        _zz_27 = pfunc_tb_36_imag;
      end
      7'b0100101 : begin
        _zz_25 = pindx_tb_37;
        _zz_26 = pfunc_tb_37_real;
        _zz_27 = pfunc_tb_37_imag;
      end
      7'b0100110 : begin
        _zz_25 = pindx_tb_38;
        _zz_26 = pfunc_tb_38_real;
        _zz_27 = pfunc_tb_38_imag;
      end
      7'b0100111 : begin
        _zz_25 = pindx_tb_39;
        _zz_26 = pfunc_tb_39_real;
        _zz_27 = pfunc_tb_39_imag;
      end
      7'b0101000 : begin
        _zz_25 = pindx_tb_40;
        _zz_26 = pfunc_tb_40_real;
        _zz_27 = pfunc_tb_40_imag;
      end
      7'b0101001 : begin
        _zz_25 = pindx_tb_41;
        _zz_26 = pfunc_tb_41_real;
        _zz_27 = pfunc_tb_41_imag;
      end
      7'b0101010 : begin
        _zz_25 = pindx_tb_42;
        _zz_26 = pfunc_tb_42_real;
        _zz_27 = pfunc_tb_42_imag;
      end
      7'b0101011 : begin
        _zz_25 = pindx_tb_43;
        _zz_26 = pfunc_tb_43_real;
        _zz_27 = pfunc_tb_43_imag;
      end
      7'b0101100 : begin
        _zz_25 = pindx_tb_44;
        _zz_26 = pfunc_tb_44_real;
        _zz_27 = pfunc_tb_44_imag;
      end
      7'b0101101 : begin
        _zz_25 = pindx_tb_45;
        _zz_26 = pfunc_tb_45_real;
        _zz_27 = pfunc_tb_45_imag;
      end
      7'b0101110 : begin
        _zz_25 = pindx_tb_46;
        _zz_26 = pfunc_tb_46_real;
        _zz_27 = pfunc_tb_46_imag;
      end
      7'b0101111 : begin
        _zz_25 = pindx_tb_47;
        _zz_26 = pfunc_tb_47_real;
        _zz_27 = pfunc_tb_47_imag;
      end
      7'b0110000 : begin
        _zz_25 = pindx_tb_48;
        _zz_26 = pfunc_tb_48_real;
        _zz_27 = pfunc_tb_48_imag;
      end
      7'b0110001 : begin
        _zz_25 = pindx_tb_49;
        _zz_26 = pfunc_tb_49_real;
        _zz_27 = pfunc_tb_49_imag;
      end
      7'b0110010 : begin
        _zz_25 = pindx_tb_50;
        _zz_26 = pfunc_tb_50_real;
        _zz_27 = pfunc_tb_50_imag;
      end
      7'b0110011 : begin
        _zz_25 = pindx_tb_51;
        _zz_26 = pfunc_tb_51_real;
        _zz_27 = pfunc_tb_51_imag;
      end
      7'b0110100 : begin
        _zz_25 = pindx_tb_52;
        _zz_26 = pfunc_tb_52_real;
        _zz_27 = pfunc_tb_52_imag;
      end
      7'b0110101 : begin
        _zz_25 = pindx_tb_53;
        _zz_26 = pfunc_tb_53_real;
        _zz_27 = pfunc_tb_53_imag;
      end
      7'b0110110 : begin
        _zz_25 = pindx_tb_54;
        _zz_26 = pfunc_tb_54_real;
        _zz_27 = pfunc_tb_54_imag;
      end
      7'b0110111 : begin
        _zz_25 = pindx_tb_55;
        _zz_26 = pfunc_tb_55_real;
        _zz_27 = pfunc_tb_55_imag;
      end
      7'b0111000 : begin
        _zz_25 = pindx_tb_56;
        _zz_26 = pfunc_tb_56_real;
        _zz_27 = pfunc_tb_56_imag;
      end
      7'b0111001 : begin
        _zz_25 = pindx_tb_57;
        _zz_26 = pfunc_tb_57_real;
        _zz_27 = pfunc_tb_57_imag;
      end
      7'b0111010 : begin
        _zz_25 = pindx_tb_58;
        _zz_26 = pfunc_tb_58_real;
        _zz_27 = pfunc_tb_58_imag;
      end
      7'b0111011 : begin
        _zz_25 = pindx_tb_59;
        _zz_26 = pfunc_tb_59_real;
        _zz_27 = pfunc_tb_59_imag;
      end
      7'b0111100 : begin
        _zz_25 = pindx_tb_60;
        _zz_26 = pfunc_tb_60_real;
        _zz_27 = pfunc_tb_60_imag;
      end
      7'b0111101 : begin
        _zz_25 = pindx_tb_61;
        _zz_26 = pfunc_tb_61_real;
        _zz_27 = pfunc_tb_61_imag;
      end
      7'b0111110 : begin
        _zz_25 = pindx_tb_62;
        _zz_26 = pfunc_tb_62_real;
        _zz_27 = pfunc_tb_62_imag;
      end
      7'b0111111 : begin
        _zz_25 = pindx_tb_63;
        _zz_26 = pfunc_tb_63_real;
        _zz_27 = pfunc_tb_63_imag;
      end
      7'b1000000 : begin
        _zz_25 = pindx_tb_64;
        _zz_26 = pfunc_tb_64_real;
        _zz_27 = pfunc_tb_64_imag;
      end
      7'b1000001 : begin
        _zz_25 = pindx_tb_65;
        _zz_26 = pfunc_tb_65_real;
        _zz_27 = pfunc_tb_65_imag;
      end
      7'b1000010 : begin
        _zz_25 = pindx_tb_66;
        _zz_26 = pfunc_tb_66_real;
        _zz_27 = pfunc_tb_66_imag;
      end
      7'b1000011 : begin
        _zz_25 = pindx_tb_67;
        _zz_26 = pfunc_tb_67_real;
        _zz_27 = pfunc_tb_67_imag;
      end
      7'b1000100 : begin
        _zz_25 = pindx_tb_68;
        _zz_26 = pfunc_tb_68_real;
        _zz_27 = pfunc_tb_68_imag;
      end
      7'b1000101 : begin
        _zz_25 = pindx_tb_69;
        _zz_26 = pfunc_tb_69_real;
        _zz_27 = pfunc_tb_69_imag;
      end
      7'b1000110 : begin
        _zz_25 = pindx_tb_70;
        _zz_26 = pfunc_tb_70_real;
        _zz_27 = pfunc_tb_70_imag;
      end
      7'b1000111 : begin
        _zz_25 = pindx_tb_71;
        _zz_26 = pfunc_tb_71_real;
        _zz_27 = pfunc_tb_71_imag;
      end
      7'b1001000 : begin
        _zz_25 = pindx_tb_72;
        _zz_26 = pfunc_tb_72_real;
        _zz_27 = pfunc_tb_72_imag;
      end
      7'b1001001 : begin
        _zz_25 = pindx_tb_73;
        _zz_26 = pfunc_tb_73_real;
        _zz_27 = pfunc_tb_73_imag;
      end
      7'b1001010 : begin
        _zz_25 = pindx_tb_74;
        _zz_26 = pfunc_tb_74_real;
        _zz_27 = pfunc_tb_74_imag;
      end
      7'b1001011 : begin
        _zz_25 = pindx_tb_75;
        _zz_26 = pfunc_tb_75_real;
        _zz_27 = pfunc_tb_75_imag;
      end
      7'b1001100 : begin
        _zz_25 = pindx_tb_76;
        _zz_26 = pfunc_tb_76_real;
        _zz_27 = pfunc_tb_76_imag;
      end
      7'b1001101 : begin
        _zz_25 = pindx_tb_77;
        _zz_26 = pfunc_tb_77_real;
        _zz_27 = pfunc_tb_77_imag;
      end
      7'b1001110 : begin
        _zz_25 = pindx_tb_78;
        _zz_26 = pfunc_tb_78_real;
        _zz_27 = pfunc_tb_78_imag;
      end
      7'b1001111 : begin
        _zz_25 = pindx_tb_79;
        _zz_26 = pfunc_tb_79_real;
        _zz_27 = pfunc_tb_79_imag;
      end
      7'b1010000 : begin
        _zz_25 = pindx_tb_80;
        _zz_26 = pfunc_tb_80_real;
        _zz_27 = pfunc_tb_80_imag;
      end
      7'b1010001 : begin
        _zz_25 = pindx_tb_81;
        _zz_26 = pfunc_tb_81_real;
        _zz_27 = pfunc_tb_81_imag;
      end
      7'b1010010 : begin
        _zz_25 = pindx_tb_82;
        _zz_26 = pfunc_tb_82_real;
        _zz_27 = pfunc_tb_82_imag;
      end
      7'b1010011 : begin
        _zz_25 = pindx_tb_83;
        _zz_26 = pfunc_tb_83_real;
        _zz_27 = pfunc_tb_83_imag;
      end
      7'b1010100 : begin
        _zz_25 = pindx_tb_84;
        _zz_26 = pfunc_tb_84_real;
        _zz_27 = pfunc_tb_84_imag;
      end
      7'b1010101 : begin
        _zz_25 = pindx_tb_85;
        _zz_26 = pfunc_tb_85_real;
        _zz_27 = pfunc_tb_85_imag;
      end
      7'b1010110 : begin
        _zz_25 = pindx_tb_86;
        _zz_26 = pfunc_tb_86_real;
        _zz_27 = pfunc_tb_86_imag;
      end
      7'b1010111 : begin
        _zz_25 = pindx_tb_87;
        _zz_26 = pfunc_tb_87_real;
        _zz_27 = pfunc_tb_87_imag;
      end
      7'b1011000 : begin
        _zz_25 = pindx_tb_88;
        _zz_26 = pfunc_tb_88_real;
        _zz_27 = pfunc_tb_88_imag;
      end
      7'b1011001 : begin
        _zz_25 = pindx_tb_89;
        _zz_26 = pfunc_tb_89_real;
        _zz_27 = pfunc_tb_89_imag;
      end
      7'b1011010 : begin
        _zz_25 = pindx_tb_90;
        _zz_26 = pfunc_tb_90_real;
        _zz_27 = pfunc_tb_90_imag;
      end
      7'b1011011 : begin
        _zz_25 = pindx_tb_91;
        _zz_26 = pfunc_tb_91_real;
        _zz_27 = pfunc_tb_91_imag;
      end
      7'b1011100 : begin
        _zz_25 = pindx_tb_92;
        _zz_26 = pfunc_tb_92_real;
        _zz_27 = pfunc_tb_92_imag;
      end
      7'b1011101 : begin
        _zz_25 = pindx_tb_93;
        _zz_26 = pfunc_tb_93_real;
        _zz_27 = pfunc_tb_93_imag;
      end
      7'b1011110 : begin
        _zz_25 = pindx_tb_94;
        _zz_26 = pfunc_tb_94_real;
        _zz_27 = pfunc_tb_94_imag;
      end
      7'b1011111 : begin
        _zz_25 = pindx_tb_95;
        _zz_26 = pfunc_tb_95_real;
        _zz_27 = pfunc_tb_95_imag;
      end
      7'b1100000 : begin
        _zz_25 = pindx_tb_96;
        _zz_26 = pfunc_tb_96_real;
        _zz_27 = pfunc_tb_96_imag;
      end
      7'b1100001 : begin
        _zz_25 = pindx_tb_97;
        _zz_26 = pfunc_tb_97_real;
        _zz_27 = pfunc_tb_97_imag;
      end
      7'b1100010 : begin
        _zz_25 = pindx_tb_98;
        _zz_26 = pfunc_tb_98_real;
        _zz_27 = pfunc_tb_98_imag;
      end
      7'b1100011 : begin
        _zz_25 = pindx_tb_99;
        _zz_26 = pfunc_tb_99_real;
        _zz_27 = pfunc_tb_99_imag;
      end
      7'b1100100 : begin
        _zz_25 = pindx_tb_100;
        _zz_26 = pfunc_tb_100_real;
        _zz_27 = pfunc_tb_100_imag;
      end
      7'b1100101 : begin
        _zz_25 = pindx_tb_101;
        _zz_26 = pfunc_tb_101_real;
        _zz_27 = pfunc_tb_101_imag;
      end
      7'b1100110 : begin
        _zz_25 = pindx_tb_102;
        _zz_26 = pfunc_tb_102_real;
        _zz_27 = pfunc_tb_102_imag;
      end
      7'b1100111 : begin
        _zz_25 = pindx_tb_103;
        _zz_26 = pfunc_tb_103_real;
        _zz_27 = pfunc_tb_103_imag;
      end
      7'b1101000 : begin
        _zz_25 = pindx_tb_104;
        _zz_26 = pfunc_tb_104_real;
        _zz_27 = pfunc_tb_104_imag;
      end
      7'b1101001 : begin
        _zz_25 = pindx_tb_105;
        _zz_26 = pfunc_tb_105_real;
        _zz_27 = pfunc_tb_105_imag;
      end
      7'b1101010 : begin
        _zz_25 = pindx_tb_106;
        _zz_26 = pfunc_tb_106_real;
        _zz_27 = pfunc_tb_106_imag;
      end
      7'b1101011 : begin
        _zz_25 = pindx_tb_107;
        _zz_26 = pfunc_tb_107_real;
        _zz_27 = pfunc_tb_107_imag;
      end
      7'b1101100 : begin
        _zz_25 = pindx_tb_108;
        _zz_26 = pfunc_tb_108_real;
        _zz_27 = pfunc_tb_108_imag;
      end
      7'b1101101 : begin
        _zz_25 = pindx_tb_109;
        _zz_26 = pfunc_tb_109_real;
        _zz_27 = pfunc_tb_109_imag;
      end
      7'b1101110 : begin
        _zz_25 = pindx_tb_110;
        _zz_26 = pfunc_tb_110_real;
        _zz_27 = pfunc_tb_110_imag;
      end
      7'b1101111 : begin
        _zz_25 = pindx_tb_111;
        _zz_26 = pfunc_tb_111_real;
        _zz_27 = pfunc_tb_111_imag;
      end
      7'b1110000 : begin
        _zz_25 = pindx_tb_112;
        _zz_26 = pfunc_tb_112_real;
        _zz_27 = pfunc_tb_112_imag;
      end
      7'b1110001 : begin
        _zz_25 = pindx_tb_113;
        _zz_26 = pfunc_tb_113_real;
        _zz_27 = pfunc_tb_113_imag;
      end
      7'b1110010 : begin
        _zz_25 = pindx_tb_114;
        _zz_26 = pfunc_tb_114_real;
        _zz_27 = pfunc_tb_114_imag;
      end
      7'b1110011 : begin
        _zz_25 = pindx_tb_115;
        _zz_26 = pfunc_tb_115_real;
        _zz_27 = pfunc_tb_115_imag;
      end
      7'b1110100 : begin
        _zz_25 = pindx_tb_116;
        _zz_26 = pfunc_tb_116_real;
        _zz_27 = pfunc_tb_116_imag;
      end
      7'b1110101 : begin
        _zz_25 = pindx_tb_117;
        _zz_26 = pfunc_tb_117_real;
        _zz_27 = pfunc_tb_117_imag;
      end
      7'b1110110 : begin
        _zz_25 = pindx_tb_118;
        _zz_26 = pfunc_tb_118_real;
        _zz_27 = pfunc_tb_118_imag;
      end
      7'b1110111 : begin
        _zz_25 = pindx_tb_119;
        _zz_26 = pfunc_tb_119_real;
        _zz_27 = pfunc_tb_119_imag;
      end
      7'b1111000 : begin
        _zz_25 = pindx_tb_120;
        _zz_26 = pfunc_tb_120_real;
        _zz_27 = pfunc_tb_120_imag;
      end
      7'b1111001 : begin
        _zz_25 = pindx_tb_121;
        _zz_26 = pfunc_tb_121_real;
        _zz_27 = pfunc_tb_121_imag;
      end
      7'b1111010 : begin
        _zz_25 = pindx_tb_122;
        _zz_26 = pfunc_tb_122_real;
        _zz_27 = pfunc_tb_122_imag;
      end
      7'b1111011 : begin
        _zz_25 = pindx_tb_123;
        _zz_26 = pfunc_tb_123_real;
        _zz_27 = pfunc_tb_123_imag;
      end
      7'b1111100 : begin
        _zz_25 = pindx_tb_124;
        _zz_26 = pfunc_tb_124_real;
        _zz_27 = pfunc_tb_124_imag;
      end
      7'b1111101 : begin
        _zz_25 = pindx_tb_125;
        _zz_26 = pfunc_tb_125_real;
        _zz_27 = pfunc_tb_125_imag;
      end
      7'b1111110 : begin
        _zz_25 = pindx_tb_126;
        _zz_26 = pfunc_tb_126_real;
        _zz_27 = pfunc_tb_126_imag;
      end
      default : begin
        _zz_25 = pindx_tb_127;
        _zz_26 = pfunc_tb_127_real;
        _zz_27 = pfunc_tb_127_imag;
      end
    endcase
  end

  assign fx_period = 32'h00010000;
  assign _zz_1 = _zz_31[31 : 0];
  always @ (*) begin
    if(fixTo_dout[31])begin
      indx = ($signed(_zz_1) + $signed(fx_period));
    end else begin
      indx = _zz_1;
    end
  end

  assign pindx_tb_0 = 32'h00000200;
  assign pindx_tb_1 = 32'h00000400;
  assign pindx_tb_2 = 32'h00000600;
  assign pindx_tb_3 = 32'h00000800;
  assign pindx_tb_4 = 32'h00000a00;
  assign pindx_tb_5 = 32'h00000c00;
  assign pindx_tb_6 = 32'h00000e00;
  assign pindx_tb_7 = 32'h00001000;
  assign pindx_tb_8 = 32'h00001200;
  assign pindx_tb_9 = 32'h00001400;
  assign pindx_tb_10 = 32'h00001600;
  assign pindx_tb_11 = 32'h00001800;
  assign pindx_tb_12 = 32'h00001a00;
  assign pindx_tb_13 = 32'h00001c00;
  assign pindx_tb_14 = 32'h00001e00;
  assign pindx_tb_15 = 32'h00002000;
  assign pindx_tb_16 = 32'h00002200;
  assign pindx_tb_17 = 32'h00002400;
  assign pindx_tb_18 = 32'h00002600;
  assign pindx_tb_19 = 32'h00002800;
  assign pindx_tb_20 = 32'h00002a00;
  assign pindx_tb_21 = 32'h00002c00;
  assign pindx_tb_22 = 32'h00002e00;
  assign pindx_tb_23 = 32'h00003000;
  assign pindx_tb_24 = 32'h00003200;
  assign pindx_tb_25 = 32'h00003400;
  assign pindx_tb_26 = 32'h00003600;
  assign pindx_tb_27 = 32'h00003800;
  assign pindx_tb_28 = 32'h00003a00;
  assign pindx_tb_29 = 32'h00003c00;
  assign pindx_tb_30 = 32'h00003e00;
  assign pindx_tb_31 = 32'h00004000;
  assign pindx_tb_32 = 32'h00004200;
  assign pindx_tb_33 = 32'h00004400;
  assign pindx_tb_34 = 32'h00004600;
  assign pindx_tb_35 = 32'h00004800;
  assign pindx_tb_36 = 32'h00004a00;
  assign pindx_tb_37 = 32'h00004c00;
  assign pindx_tb_38 = 32'h00004e00;
  assign pindx_tb_39 = 32'h00005000;
  assign pindx_tb_40 = 32'h00005200;
  assign pindx_tb_41 = 32'h00005400;
  assign pindx_tb_42 = 32'h00005600;
  assign pindx_tb_43 = 32'h00005800;
  assign pindx_tb_44 = 32'h00005a00;
  assign pindx_tb_45 = 32'h00005c00;
  assign pindx_tb_46 = 32'h00005e00;
  assign pindx_tb_47 = 32'h00006000;
  assign pindx_tb_48 = 32'h00006200;
  assign pindx_tb_49 = 32'h00006400;
  assign pindx_tb_50 = 32'h00006600;
  assign pindx_tb_51 = 32'h00006800;
  assign pindx_tb_52 = 32'h00006a00;
  assign pindx_tb_53 = 32'h00006c00;
  assign pindx_tb_54 = 32'h00006e00;
  assign pindx_tb_55 = 32'h00007000;
  assign pindx_tb_56 = 32'h00007200;
  assign pindx_tb_57 = 32'h00007400;
  assign pindx_tb_58 = 32'h00007600;
  assign pindx_tb_59 = 32'h00007800;
  assign pindx_tb_60 = 32'h00007a00;
  assign pindx_tb_61 = 32'h00007c00;
  assign pindx_tb_62 = 32'h00007e00;
  assign pindx_tb_63 = 32'h00008000;
  assign pindx_tb_64 = 32'h00008200;
  assign pindx_tb_65 = 32'h00008400;
  assign pindx_tb_66 = 32'h00008600;
  assign pindx_tb_67 = 32'h00008800;
  assign pindx_tb_68 = 32'h00008a00;
  assign pindx_tb_69 = 32'h00008c00;
  assign pindx_tb_70 = 32'h00008e00;
  assign pindx_tb_71 = 32'h00009000;
  assign pindx_tb_72 = 32'h00009200;
  assign pindx_tb_73 = 32'h00009400;
  assign pindx_tb_74 = 32'h00009600;
  assign pindx_tb_75 = 32'h00009800;
  assign pindx_tb_76 = 32'h00009a00;
  assign pindx_tb_77 = 32'h00009c00;
  assign pindx_tb_78 = 32'h00009e00;
  assign pindx_tb_79 = 32'h0000a000;
  assign pindx_tb_80 = 32'h0000a200;
  assign pindx_tb_81 = 32'h0000a400;
  assign pindx_tb_82 = 32'h0000a600;
  assign pindx_tb_83 = 32'h0000a800;
  assign pindx_tb_84 = 32'h0000aa00;
  assign pindx_tb_85 = 32'h0000ac00;
  assign pindx_tb_86 = 32'h0000ae00;
  assign pindx_tb_87 = 32'h0000b000;
  assign pindx_tb_88 = 32'h0000b200;
  assign pindx_tb_89 = 32'h0000b400;
  assign pindx_tb_90 = 32'h0000b600;
  assign pindx_tb_91 = 32'h0000b800;
  assign pindx_tb_92 = 32'h0000ba00;
  assign pindx_tb_93 = 32'h0000bc00;
  assign pindx_tb_94 = 32'h0000be00;
  assign pindx_tb_95 = 32'h0000c000;
  assign pindx_tb_96 = 32'h0000c200;
  assign pindx_tb_97 = 32'h0000c400;
  assign pindx_tb_98 = 32'h0000c600;
  assign pindx_tb_99 = 32'h0000c800;
  assign pindx_tb_100 = 32'h0000ca00;
  assign pindx_tb_101 = 32'h0000cc00;
  assign pindx_tb_102 = 32'h0000ce00;
  assign pindx_tb_103 = 32'h0000d000;
  assign pindx_tb_104 = 32'h0000d200;
  assign pindx_tb_105 = 32'h0000d400;
  assign pindx_tb_106 = 32'h0000d600;
  assign pindx_tb_107 = 32'h0000d800;
  assign pindx_tb_108 = 32'h0000da00;
  assign pindx_tb_109 = 32'h0000dc00;
  assign pindx_tb_110 = 32'h0000de00;
  assign pindx_tb_111 = 32'h0000e000;
  assign pindx_tb_112 = 32'h0000e200;
  assign pindx_tb_113 = 32'h0000e400;
  assign pindx_tb_114 = 32'h0000e600;
  assign pindx_tb_115 = 32'h0000e800;
  assign pindx_tb_116 = 32'h0000ea00;
  assign pindx_tb_117 = 32'h0000ec00;
  assign pindx_tb_118 = 32'h0000ee00;
  assign pindx_tb_119 = 32'h0000f000;
  assign pindx_tb_120 = 32'h0000f200;
  assign pindx_tb_121 = 32'h0000f400;
  assign pindx_tb_122 = 32'h0000f600;
  assign pindx_tb_123 = 32'h0000f800;
  assign pindx_tb_124 = 32'h0000fa00;
  assign pindx_tb_125 = 32'h0000fc00;
  assign pindx_tb_126 = 32'h0000fe00;
  assign pindx_tb_127 = 32'h00010000;
  assign pfunc_tb_0_real = 32'h0000ffb1;
  assign pfunc_tb_0_imag = 32'h00000c8f;
  assign pfunc_tb_1_real = 32'h0000fec4;
  assign pfunc_tb_1_imag = 32'h00001917;
  assign pfunc_tb_2_real = 32'h0000fd3a;
  assign pfunc_tb_2_imag = 32'h00002590;
  assign pfunc_tb_3_real = 32'h0000fb14;
  assign pfunc_tb_3_imag = 32'h000031f1;
  assign pfunc_tb_4_real = 32'h0000f853;
  assign pfunc_tb_4_imag = 32'h00003e33;
  assign pfunc_tb_5_real = 32'h0000f4fa;
  assign pfunc_tb_5_imag = 32'h00004a50;
  assign pfunc_tb_6_real = 32'h0000f109;
  assign pfunc_tb_6_imag = 32'h0000563e;
  assign pfunc_tb_7_real = 32'h0000ec83;
  assign pfunc_tb_7_imag = 32'h000061f7;
  assign pfunc_tb_8_real = 32'h0000e76b;
  assign pfunc_tb_8_imag = 32'h00006d74;
  assign pfunc_tb_9_real = 32'h0000e1c5;
  assign pfunc_tb_9_imag = 32'h000078ad;
  assign pfunc_tb_10_real = 32'h0000db94;
  assign pfunc_tb_10_imag = 32'h0000839c;
  assign pfunc_tb_11_real = 32'h0000d4db;
  assign pfunc_tb_11_imag = 32'h00008e39;
  assign pfunc_tb_12_real = 32'h0000cd9f;
  assign pfunc_tb_12_imag = 32'h0000987f;
  assign pfunc_tb_13_real = 32'h0000c5e4;
  assign pfunc_tb_13_imag = 32'h0000a267;
  assign pfunc_tb_14_real = 32'h0000bdae;
  assign pfunc_tb_14_imag = 32'h0000abeb;
  assign pfunc_tb_15_real = 32'h0000b504;
  assign pfunc_tb_15_imag = 32'h0000b504;
  assign pfunc_tb_16_real = 32'h0000abeb;
  assign pfunc_tb_16_imag = 32'h0000bdae;
  assign pfunc_tb_17_real = 32'h0000a267;
  assign pfunc_tb_17_imag = 32'h0000c5e4;
  assign pfunc_tb_18_real = 32'h0000987f;
  assign pfunc_tb_18_imag = 32'h0000cd9f;
  assign pfunc_tb_19_real = 32'h00008e39;
  assign pfunc_tb_19_imag = 32'h0000d4db;
  assign pfunc_tb_20_real = 32'h0000839c;
  assign pfunc_tb_20_imag = 32'h0000db94;
  assign pfunc_tb_21_real = 32'h000078ad;
  assign pfunc_tb_21_imag = 32'h0000e1c5;
  assign pfunc_tb_22_real = 32'h00006d74;
  assign pfunc_tb_22_imag = 32'h0000e76b;
  assign pfunc_tb_23_real = 32'h000061f7;
  assign pfunc_tb_23_imag = 32'h0000ec83;
  assign pfunc_tb_24_real = 32'h0000563e;
  assign pfunc_tb_24_imag = 32'h0000f109;
  assign pfunc_tb_25_real = 32'h00004a50;
  assign pfunc_tb_25_imag = 32'h0000f4fa;
  assign pfunc_tb_26_real = 32'h00003e33;
  assign pfunc_tb_26_imag = 32'h0000f853;
  assign pfunc_tb_27_real = 32'h000031f1;
  assign pfunc_tb_27_imag = 32'h0000fb14;
  assign pfunc_tb_28_real = 32'h00002590;
  assign pfunc_tb_28_imag = 32'h0000fd3a;
  assign pfunc_tb_29_real = 32'h00001917;
  assign pfunc_tb_29_imag = 32'h0000fec4;
  assign pfunc_tb_30_real = 32'h00000c8f;
  assign pfunc_tb_30_imag = 32'h0000ffb1;
  assign pfunc_tb_31_real = 32'h0;
  assign pfunc_tb_31_imag = 32'h00010000;
  assign pfunc_tb_32_real = 32'hfffff371;
  assign pfunc_tb_32_imag = 32'h0000ffb1;
  assign pfunc_tb_33_real = 32'hffffe6e9;
  assign pfunc_tb_33_imag = 32'h0000fec4;
  assign pfunc_tb_34_real = 32'hffffda70;
  assign pfunc_tb_34_imag = 32'h0000fd3a;
  assign pfunc_tb_35_real = 32'hffffce0f;
  assign pfunc_tb_35_imag = 32'h0000fb14;
  assign pfunc_tb_36_real = 32'hffffc1cd;
  assign pfunc_tb_36_imag = 32'h0000f853;
  assign pfunc_tb_37_real = 32'hffffb5b0;
  assign pfunc_tb_37_imag = 32'h0000f4fa;
  assign pfunc_tb_38_real = 32'hffffa9c2;
  assign pfunc_tb_38_imag = 32'h0000f109;
  assign pfunc_tb_39_real = 32'hffff9e09;
  assign pfunc_tb_39_imag = 32'h0000ec83;
  assign pfunc_tb_40_real = 32'hffff928c;
  assign pfunc_tb_40_imag = 32'h0000e76b;
  assign pfunc_tb_41_real = 32'hffff8753;
  assign pfunc_tb_41_imag = 32'h0000e1c5;
  assign pfunc_tb_42_real = 32'hffff7c64;
  assign pfunc_tb_42_imag = 32'h0000db94;
  assign pfunc_tb_43_real = 32'hffff71c7;
  assign pfunc_tb_43_imag = 32'h0000d4db;
  assign pfunc_tb_44_real = 32'hffff6781;
  assign pfunc_tb_44_imag = 32'h0000cd9f;
  assign pfunc_tb_45_real = 32'hffff5d99;
  assign pfunc_tb_45_imag = 32'h0000c5e4;
  assign pfunc_tb_46_real = 32'hffff5415;
  assign pfunc_tb_46_imag = 32'h0000bdae;
  assign pfunc_tb_47_real = 32'hffff4afc;
  assign pfunc_tb_47_imag = 32'h0000b504;
  assign pfunc_tb_48_real = 32'hffff4252;
  assign pfunc_tb_48_imag = 32'h0000abeb;
  assign pfunc_tb_49_real = 32'hffff3a1c;
  assign pfunc_tb_49_imag = 32'h0000a267;
  assign pfunc_tb_50_real = 32'hffff3261;
  assign pfunc_tb_50_imag = 32'h0000987f;
  assign pfunc_tb_51_real = 32'hffff2b25;
  assign pfunc_tb_51_imag = 32'h00008e39;
  assign pfunc_tb_52_real = 32'hffff246c;
  assign pfunc_tb_52_imag = 32'h0000839c;
  assign pfunc_tb_53_real = 32'hffff1e3b;
  assign pfunc_tb_53_imag = 32'h000078ad;
  assign pfunc_tb_54_real = 32'hffff1895;
  assign pfunc_tb_54_imag = 32'h00006d74;
  assign pfunc_tb_55_real = 32'hffff137d;
  assign pfunc_tb_55_imag = 32'h000061f7;
  assign pfunc_tb_56_real = 32'hffff0ef7;
  assign pfunc_tb_56_imag = 32'h0000563e;
  assign pfunc_tb_57_real = 32'hffff0b06;
  assign pfunc_tb_57_imag = 32'h00004a50;
  assign pfunc_tb_58_real = 32'hffff07ad;
  assign pfunc_tb_58_imag = 32'h00003e33;
  assign pfunc_tb_59_real = 32'hffff04ec;
  assign pfunc_tb_59_imag = 32'h000031f1;
  assign pfunc_tb_60_real = 32'hffff02c6;
  assign pfunc_tb_60_imag = 32'h00002590;
  assign pfunc_tb_61_real = 32'hffff013c;
  assign pfunc_tb_61_imag = 32'h00001917;
  assign pfunc_tb_62_real = 32'hffff004f;
  assign pfunc_tb_62_imag = 32'h00000c8f;
  assign pfunc_tb_63_real = 32'hffff0000;
  assign pfunc_tb_63_imag = 32'h0;
  assign pfunc_tb_64_real = 32'hffff004f;
  assign pfunc_tb_64_imag = 32'hfffff371;
  assign pfunc_tb_65_real = 32'hffff013c;
  assign pfunc_tb_65_imag = 32'hffffe6e9;
  assign pfunc_tb_66_real = 32'hffff02c6;
  assign pfunc_tb_66_imag = 32'hffffda70;
  assign pfunc_tb_67_real = 32'hffff04ec;
  assign pfunc_tb_67_imag = 32'hffffce0f;
  assign pfunc_tb_68_real = 32'hffff07ad;
  assign pfunc_tb_68_imag = 32'hffffc1cd;
  assign pfunc_tb_69_real = 32'hffff0b06;
  assign pfunc_tb_69_imag = 32'hffffb5b0;
  assign pfunc_tb_70_real = 32'hffff0ef7;
  assign pfunc_tb_70_imag = 32'hffffa9c2;
  assign pfunc_tb_71_real = 32'hffff137d;
  assign pfunc_tb_71_imag = 32'hffff9e09;
  assign pfunc_tb_72_real = 32'hffff1895;
  assign pfunc_tb_72_imag = 32'hffff928c;
  assign pfunc_tb_73_real = 32'hffff1e3b;
  assign pfunc_tb_73_imag = 32'hffff8753;
  assign pfunc_tb_74_real = 32'hffff246c;
  assign pfunc_tb_74_imag = 32'hffff7c64;
  assign pfunc_tb_75_real = 32'hffff2b25;
  assign pfunc_tb_75_imag = 32'hffff71c7;
  assign pfunc_tb_76_real = 32'hffff3261;
  assign pfunc_tb_76_imag = 32'hffff6781;
  assign pfunc_tb_77_real = 32'hffff3a1c;
  assign pfunc_tb_77_imag = 32'hffff5d99;
  assign pfunc_tb_78_real = 32'hffff4252;
  assign pfunc_tb_78_imag = 32'hffff5415;
  assign pfunc_tb_79_real = 32'hffff4afc;
  assign pfunc_tb_79_imag = 32'hffff4afc;
  assign pfunc_tb_80_real = 32'hffff5415;
  assign pfunc_tb_80_imag = 32'hffff4252;
  assign pfunc_tb_81_real = 32'hffff5d99;
  assign pfunc_tb_81_imag = 32'hffff3a1c;
  assign pfunc_tb_82_real = 32'hffff6781;
  assign pfunc_tb_82_imag = 32'hffff3261;
  assign pfunc_tb_83_real = 32'hffff71c7;
  assign pfunc_tb_83_imag = 32'hffff2b25;
  assign pfunc_tb_84_real = 32'hffff7c64;
  assign pfunc_tb_84_imag = 32'hffff246c;
  assign pfunc_tb_85_real = 32'hffff8753;
  assign pfunc_tb_85_imag = 32'hffff1e3b;
  assign pfunc_tb_86_real = 32'hffff928c;
  assign pfunc_tb_86_imag = 32'hffff1895;
  assign pfunc_tb_87_real = 32'hffff9e09;
  assign pfunc_tb_87_imag = 32'hffff137d;
  assign pfunc_tb_88_real = 32'hffffa9c2;
  assign pfunc_tb_88_imag = 32'hffff0ef7;
  assign pfunc_tb_89_real = 32'hffffb5b0;
  assign pfunc_tb_89_imag = 32'hffff0b06;
  assign pfunc_tb_90_real = 32'hffffc1cd;
  assign pfunc_tb_90_imag = 32'hffff07ad;
  assign pfunc_tb_91_real = 32'hffffce0f;
  assign pfunc_tb_91_imag = 32'hffff04ec;
  assign pfunc_tb_92_real = 32'hffffda70;
  assign pfunc_tb_92_imag = 32'hffff02c6;
  assign pfunc_tb_93_real = 32'hffffe6e9;
  assign pfunc_tb_93_imag = 32'hffff013c;
  assign pfunc_tb_94_real = 32'hfffff371;
  assign pfunc_tb_94_imag = 32'hffff004f;
  assign pfunc_tb_95_real = 32'h0;
  assign pfunc_tb_95_imag = 32'hffff0000;
  assign pfunc_tb_96_real = 32'h00000c8f;
  assign pfunc_tb_96_imag = 32'hffff004f;
  assign pfunc_tb_97_real = 32'h00001917;
  assign pfunc_tb_97_imag = 32'hffff013c;
  assign pfunc_tb_98_real = 32'h00002590;
  assign pfunc_tb_98_imag = 32'hffff02c6;
  assign pfunc_tb_99_real = 32'h000031f1;
  assign pfunc_tb_99_imag = 32'hffff04ec;
  assign pfunc_tb_100_real = 32'h00003e33;
  assign pfunc_tb_100_imag = 32'hffff07ad;
  assign pfunc_tb_101_real = 32'h00004a50;
  assign pfunc_tb_101_imag = 32'hffff0b06;
  assign pfunc_tb_102_real = 32'h0000563e;
  assign pfunc_tb_102_imag = 32'hffff0ef7;
  assign pfunc_tb_103_real = 32'h000061f7;
  assign pfunc_tb_103_imag = 32'hffff137d;
  assign pfunc_tb_104_real = 32'h00006d74;
  assign pfunc_tb_104_imag = 32'hffff1895;
  assign pfunc_tb_105_real = 32'h000078ad;
  assign pfunc_tb_105_imag = 32'hffff1e3b;
  assign pfunc_tb_106_real = 32'h0000839c;
  assign pfunc_tb_106_imag = 32'hffff246c;
  assign pfunc_tb_107_real = 32'h00008e39;
  assign pfunc_tb_107_imag = 32'hffff2b25;
  assign pfunc_tb_108_real = 32'h0000987f;
  assign pfunc_tb_108_imag = 32'hffff3261;
  assign pfunc_tb_109_real = 32'h0000a267;
  assign pfunc_tb_109_imag = 32'hffff3a1c;
  assign pfunc_tb_110_real = 32'h0000abeb;
  assign pfunc_tb_110_imag = 32'hffff4252;
  assign pfunc_tb_111_real = 32'h0000b504;
  assign pfunc_tb_111_imag = 32'hffff4afc;
  assign pfunc_tb_112_real = 32'h0000bdae;
  assign pfunc_tb_112_imag = 32'hffff5415;
  assign pfunc_tb_113_real = 32'h0000c5e4;
  assign pfunc_tb_113_imag = 32'hffff5d99;
  assign pfunc_tb_114_real = 32'h0000cd9f;
  assign pfunc_tb_114_imag = 32'hffff6781;
  assign pfunc_tb_115_real = 32'h0000d4db;
  assign pfunc_tb_115_imag = 32'hffff71c7;
  assign pfunc_tb_116_real = 32'h0000db94;
  assign pfunc_tb_116_imag = 32'hffff7c64;
  assign pfunc_tb_117_real = 32'h0000e1c5;
  assign pfunc_tb_117_imag = 32'hffff8753;
  assign pfunc_tb_118_real = 32'h0000e76b;
  assign pfunc_tb_118_imag = 32'hffff928c;
  assign pfunc_tb_119_real = 32'h0000ec83;
  assign pfunc_tb_119_imag = 32'hffff9e09;
  assign pfunc_tb_120_real = 32'h0000f109;
  assign pfunc_tb_120_imag = 32'hffffa9c2;
  assign pfunc_tb_121_real = 32'h0000f4fa;
  assign pfunc_tb_121_imag = 32'hffffb5b0;
  assign pfunc_tb_122_real = 32'h0000f853;
  assign pfunc_tb_122_imag = 32'hffffc1cd;
  assign pfunc_tb_123_real = 32'h0000fb14;
  assign pfunc_tb_123_imag = 32'hffffce0f;
  assign pfunc_tb_124_real = 32'h0000fd3a;
  assign pfunc_tb_124_imag = 32'hffffda70;
  assign pfunc_tb_125_real = 32'h0000fec4;
  assign pfunc_tb_125_imag = 32'hffffe6e9;
  assign pfunc_tb_126_real = 32'h0000ffb1;
  assign pfunc_tb_126_imag = 32'hfffff371;
  assign pfunc_tb_127_real = 32'h00010000;
  assign pfunc_tb_127_imag = 32'h0;
  assign idx_comp_0 = ($signed(indx) < $signed(pindx_tb_0));
  assign idx_comp_1 = ($signed(indx) < $signed(pindx_tb_1));
  assign idx_comp_2 = ($signed(indx) < $signed(pindx_tb_2));
  assign idx_comp_3 = ($signed(indx) < $signed(pindx_tb_3));
  assign idx_comp_4 = ($signed(indx) < $signed(pindx_tb_4));
  assign idx_comp_5 = ($signed(indx) < $signed(pindx_tb_5));
  assign idx_comp_6 = ($signed(indx) < $signed(pindx_tb_6));
  assign idx_comp_7 = ($signed(indx) < $signed(pindx_tb_7));
  assign idx_comp_8 = ($signed(indx) < $signed(pindx_tb_8));
  assign idx_comp_9 = ($signed(indx) < $signed(pindx_tb_9));
  assign idx_comp_10 = ($signed(indx) < $signed(pindx_tb_10));
  assign idx_comp_11 = ($signed(indx) < $signed(pindx_tb_11));
  assign idx_comp_12 = ($signed(indx) < $signed(pindx_tb_12));
  assign idx_comp_13 = ($signed(indx) < $signed(pindx_tb_13));
  assign idx_comp_14 = ($signed(indx) < $signed(pindx_tb_14));
  assign idx_comp_15 = ($signed(indx) < $signed(pindx_tb_15));
  assign idx_comp_16 = ($signed(indx) < $signed(pindx_tb_16));
  assign idx_comp_17 = ($signed(indx) < $signed(pindx_tb_17));
  assign idx_comp_18 = ($signed(indx) < $signed(pindx_tb_18));
  assign idx_comp_19 = ($signed(indx) < $signed(pindx_tb_19));
  assign idx_comp_20 = ($signed(indx) < $signed(pindx_tb_20));
  assign idx_comp_21 = ($signed(indx) < $signed(pindx_tb_21));
  assign idx_comp_22 = ($signed(indx) < $signed(pindx_tb_22));
  assign idx_comp_23 = ($signed(indx) < $signed(pindx_tb_23));
  assign idx_comp_24 = ($signed(indx) < $signed(pindx_tb_24));
  assign idx_comp_25 = ($signed(indx) < $signed(pindx_tb_25));
  assign idx_comp_26 = ($signed(indx) < $signed(pindx_tb_26));
  assign idx_comp_27 = ($signed(indx) < $signed(pindx_tb_27));
  assign idx_comp_28 = ($signed(indx) < $signed(pindx_tb_28));
  assign idx_comp_29 = ($signed(indx) < $signed(pindx_tb_29));
  assign idx_comp_30 = ($signed(indx) < $signed(pindx_tb_30));
  assign idx_comp_31 = ($signed(indx) < $signed(pindx_tb_31));
  assign idx_comp_32 = ($signed(indx) < $signed(pindx_tb_32));
  assign idx_comp_33 = ($signed(indx) < $signed(pindx_tb_33));
  assign idx_comp_34 = ($signed(indx) < $signed(pindx_tb_34));
  assign idx_comp_35 = ($signed(indx) < $signed(pindx_tb_35));
  assign idx_comp_36 = ($signed(indx) < $signed(pindx_tb_36));
  assign idx_comp_37 = ($signed(indx) < $signed(pindx_tb_37));
  assign idx_comp_38 = ($signed(indx) < $signed(pindx_tb_38));
  assign idx_comp_39 = ($signed(indx) < $signed(pindx_tb_39));
  assign idx_comp_40 = ($signed(indx) < $signed(pindx_tb_40));
  assign idx_comp_41 = ($signed(indx) < $signed(pindx_tb_41));
  assign idx_comp_42 = ($signed(indx) < $signed(pindx_tb_42));
  assign idx_comp_43 = ($signed(indx) < $signed(pindx_tb_43));
  assign idx_comp_44 = ($signed(indx) < $signed(pindx_tb_44));
  assign idx_comp_45 = ($signed(indx) < $signed(pindx_tb_45));
  assign idx_comp_46 = ($signed(indx) < $signed(pindx_tb_46));
  assign idx_comp_47 = ($signed(indx) < $signed(pindx_tb_47));
  assign idx_comp_48 = ($signed(indx) < $signed(pindx_tb_48));
  assign idx_comp_49 = ($signed(indx) < $signed(pindx_tb_49));
  assign idx_comp_50 = ($signed(indx) < $signed(pindx_tb_50));
  assign idx_comp_51 = ($signed(indx) < $signed(pindx_tb_51));
  assign idx_comp_52 = ($signed(indx) < $signed(pindx_tb_52));
  assign idx_comp_53 = ($signed(indx) < $signed(pindx_tb_53));
  assign idx_comp_54 = ($signed(indx) < $signed(pindx_tb_54));
  assign idx_comp_55 = ($signed(indx) < $signed(pindx_tb_55));
  assign idx_comp_56 = ($signed(indx) < $signed(pindx_tb_56));
  assign idx_comp_57 = ($signed(indx) < $signed(pindx_tb_57));
  assign idx_comp_58 = ($signed(indx) < $signed(pindx_tb_58));
  assign idx_comp_59 = ($signed(indx) < $signed(pindx_tb_59));
  assign idx_comp_60 = ($signed(indx) < $signed(pindx_tb_60));
  assign idx_comp_61 = ($signed(indx) < $signed(pindx_tb_61));
  assign idx_comp_62 = ($signed(indx) < $signed(pindx_tb_62));
  assign idx_comp_63 = ($signed(indx) < $signed(pindx_tb_63));
  assign idx_comp_64 = ($signed(indx) < $signed(pindx_tb_64));
  assign idx_comp_65 = ($signed(indx) < $signed(pindx_tb_65));
  assign idx_comp_66 = ($signed(indx) < $signed(pindx_tb_66));
  assign idx_comp_67 = ($signed(indx) < $signed(pindx_tb_67));
  assign idx_comp_68 = ($signed(indx) < $signed(pindx_tb_68));
  assign idx_comp_69 = ($signed(indx) < $signed(pindx_tb_69));
  assign idx_comp_70 = ($signed(indx) < $signed(pindx_tb_70));
  assign idx_comp_71 = ($signed(indx) < $signed(pindx_tb_71));
  assign idx_comp_72 = ($signed(indx) < $signed(pindx_tb_72));
  assign idx_comp_73 = ($signed(indx) < $signed(pindx_tb_73));
  assign idx_comp_74 = ($signed(indx) < $signed(pindx_tb_74));
  assign idx_comp_75 = ($signed(indx) < $signed(pindx_tb_75));
  assign idx_comp_76 = ($signed(indx) < $signed(pindx_tb_76));
  assign idx_comp_77 = ($signed(indx) < $signed(pindx_tb_77));
  assign idx_comp_78 = ($signed(indx) < $signed(pindx_tb_78));
  assign idx_comp_79 = ($signed(indx) < $signed(pindx_tb_79));
  assign idx_comp_80 = ($signed(indx) < $signed(pindx_tb_80));
  assign idx_comp_81 = ($signed(indx) < $signed(pindx_tb_81));
  assign idx_comp_82 = ($signed(indx) < $signed(pindx_tb_82));
  assign idx_comp_83 = ($signed(indx) < $signed(pindx_tb_83));
  assign idx_comp_84 = ($signed(indx) < $signed(pindx_tb_84));
  assign idx_comp_85 = ($signed(indx) < $signed(pindx_tb_85));
  assign idx_comp_86 = ($signed(indx) < $signed(pindx_tb_86));
  assign idx_comp_87 = ($signed(indx) < $signed(pindx_tb_87));
  assign idx_comp_88 = ($signed(indx) < $signed(pindx_tb_88));
  assign idx_comp_89 = ($signed(indx) < $signed(pindx_tb_89));
  assign idx_comp_90 = ($signed(indx) < $signed(pindx_tb_90));
  assign idx_comp_91 = ($signed(indx) < $signed(pindx_tb_91));
  assign idx_comp_92 = ($signed(indx) < $signed(pindx_tb_92));
  assign idx_comp_93 = ($signed(indx) < $signed(pindx_tb_93));
  assign idx_comp_94 = ($signed(indx) < $signed(pindx_tb_94));
  assign idx_comp_95 = ($signed(indx) < $signed(pindx_tb_95));
  assign idx_comp_96 = ($signed(indx) < $signed(pindx_tb_96));
  assign idx_comp_97 = ($signed(indx) < $signed(pindx_tb_97));
  assign idx_comp_98 = ($signed(indx) < $signed(pindx_tb_98));
  assign idx_comp_99 = ($signed(indx) < $signed(pindx_tb_99));
  assign idx_comp_100 = ($signed(indx) < $signed(pindx_tb_100));
  assign idx_comp_101 = ($signed(indx) < $signed(pindx_tb_101));
  assign idx_comp_102 = ($signed(indx) < $signed(pindx_tb_102));
  assign idx_comp_103 = ($signed(indx) < $signed(pindx_tb_103));
  assign idx_comp_104 = ($signed(indx) < $signed(pindx_tb_104));
  assign idx_comp_105 = ($signed(indx) < $signed(pindx_tb_105));
  assign idx_comp_106 = ($signed(indx) < $signed(pindx_tb_106));
  assign idx_comp_107 = ($signed(indx) < $signed(pindx_tb_107));
  assign idx_comp_108 = ($signed(indx) < $signed(pindx_tb_108));
  assign idx_comp_109 = ($signed(indx) < $signed(pindx_tb_109));
  assign idx_comp_110 = ($signed(indx) < $signed(pindx_tb_110));
  assign idx_comp_111 = ($signed(indx) < $signed(pindx_tb_111));
  assign idx_comp_112 = ($signed(indx) < $signed(pindx_tb_112));
  assign idx_comp_113 = ($signed(indx) < $signed(pindx_tb_113));
  assign idx_comp_114 = ($signed(indx) < $signed(pindx_tb_114));
  assign idx_comp_115 = ($signed(indx) < $signed(pindx_tb_115));
  assign idx_comp_116 = ($signed(indx) < $signed(pindx_tb_116));
  assign idx_comp_117 = ($signed(indx) < $signed(pindx_tb_117));
  assign idx_comp_118 = ($signed(indx) < $signed(pindx_tb_118));
  assign idx_comp_119 = ($signed(indx) < $signed(pindx_tb_119));
  assign idx_comp_120 = ($signed(indx) < $signed(pindx_tb_120));
  assign idx_comp_121 = ($signed(indx) < $signed(pindx_tb_121));
  assign idx_comp_122 = ($signed(indx) < $signed(pindx_tb_122));
  assign idx_comp_123 = ($signed(indx) < $signed(pindx_tb_123));
  assign idx_comp_124 = ($signed(indx) < $signed(pindx_tb_124));
  assign idx_comp_125 = ($signed(indx) < $signed(pindx_tb_125));
  assign idx_comp_126 = ($signed(indx) < $signed(pindx_tb_126));
  assign idx_comp_127 = ($signed(indx) < $signed(pindx_tb_127));
  always @ (*) begin
    idx_comp_vec[0] = idx_comp_127;
    idx_comp_vec[1] = idx_comp_126;
    idx_comp_vec[2] = idx_comp_125;
    idx_comp_vec[3] = idx_comp_124;
    idx_comp_vec[4] = idx_comp_123;
    idx_comp_vec[5] = idx_comp_122;
    idx_comp_vec[6] = idx_comp_121;
    idx_comp_vec[7] = idx_comp_120;
    idx_comp_vec[8] = idx_comp_119;
    idx_comp_vec[9] = idx_comp_118;
    idx_comp_vec[10] = idx_comp_117;
    idx_comp_vec[11] = idx_comp_116;
    idx_comp_vec[12] = idx_comp_115;
    idx_comp_vec[13] = idx_comp_114;
    idx_comp_vec[14] = idx_comp_113;
    idx_comp_vec[15] = idx_comp_112;
    idx_comp_vec[16] = idx_comp_111;
    idx_comp_vec[17] = idx_comp_110;
    idx_comp_vec[18] = idx_comp_109;
    idx_comp_vec[19] = idx_comp_108;
    idx_comp_vec[20] = idx_comp_107;
    idx_comp_vec[21] = idx_comp_106;
    idx_comp_vec[22] = idx_comp_105;
    idx_comp_vec[23] = idx_comp_104;
    idx_comp_vec[24] = idx_comp_103;
    idx_comp_vec[25] = idx_comp_102;
    idx_comp_vec[26] = idx_comp_101;
    idx_comp_vec[27] = idx_comp_100;
    idx_comp_vec[28] = idx_comp_99;
    idx_comp_vec[29] = idx_comp_98;
    idx_comp_vec[30] = idx_comp_97;
    idx_comp_vec[31] = idx_comp_96;
    idx_comp_vec[32] = idx_comp_95;
    idx_comp_vec[33] = idx_comp_94;
    idx_comp_vec[34] = idx_comp_93;
    idx_comp_vec[35] = idx_comp_92;
    idx_comp_vec[36] = idx_comp_91;
    idx_comp_vec[37] = idx_comp_90;
    idx_comp_vec[38] = idx_comp_89;
    idx_comp_vec[39] = idx_comp_88;
    idx_comp_vec[40] = idx_comp_87;
    idx_comp_vec[41] = idx_comp_86;
    idx_comp_vec[42] = idx_comp_85;
    idx_comp_vec[43] = idx_comp_84;
    idx_comp_vec[44] = idx_comp_83;
    idx_comp_vec[45] = idx_comp_82;
    idx_comp_vec[46] = idx_comp_81;
    idx_comp_vec[47] = idx_comp_80;
    idx_comp_vec[48] = idx_comp_79;
    idx_comp_vec[49] = idx_comp_78;
    idx_comp_vec[50] = idx_comp_77;
    idx_comp_vec[51] = idx_comp_76;
    idx_comp_vec[52] = idx_comp_75;
    idx_comp_vec[53] = idx_comp_74;
    idx_comp_vec[54] = idx_comp_73;
    idx_comp_vec[55] = idx_comp_72;
    idx_comp_vec[56] = idx_comp_71;
    idx_comp_vec[57] = idx_comp_70;
    idx_comp_vec[58] = idx_comp_69;
    idx_comp_vec[59] = idx_comp_68;
    idx_comp_vec[60] = idx_comp_67;
    idx_comp_vec[61] = idx_comp_66;
    idx_comp_vec[62] = idx_comp_65;
    idx_comp_vec[63] = idx_comp_64;
    idx_comp_vec[64] = idx_comp_63;
    idx_comp_vec[65] = idx_comp_62;
    idx_comp_vec[66] = idx_comp_61;
    idx_comp_vec[67] = idx_comp_60;
    idx_comp_vec[68] = idx_comp_59;
    idx_comp_vec[69] = idx_comp_58;
    idx_comp_vec[70] = idx_comp_57;
    idx_comp_vec[71] = idx_comp_56;
    idx_comp_vec[72] = idx_comp_55;
    idx_comp_vec[73] = idx_comp_54;
    idx_comp_vec[74] = idx_comp_53;
    idx_comp_vec[75] = idx_comp_52;
    idx_comp_vec[76] = idx_comp_51;
    idx_comp_vec[77] = idx_comp_50;
    idx_comp_vec[78] = idx_comp_49;
    idx_comp_vec[79] = idx_comp_48;
    idx_comp_vec[80] = idx_comp_47;
    idx_comp_vec[81] = idx_comp_46;
    idx_comp_vec[82] = idx_comp_45;
    idx_comp_vec[83] = idx_comp_44;
    idx_comp_vec[84] = idx_comp_43;
    idx_comp_vec[85] = idx_comp_42;
    idx_comp_vec[86] = idx_comp_41;
    idx_comp_vec[87] = idx_comp_40;
    idx_comp_vec[88] = idx_comp_39;
    idx_comp_vec[89] = idx_comp_38;
    idx_comp_vec[90] = idx_comp_37;
    idx_comp_vec[91] = idx_comp_36;
    idx_comp_vec[92] = idx_comp_35;
    idx_comp_vec[93] = idx_comp_34;
    idx_comp_vec[94] = idx_comp_33;
    idx_comp_vec[95] = idx_comp_32;
    idx_comp_vec[96] = idx_comp_31;
    idx_comp_vec[97] = idx_comp_30;
    idx_comp_vec[98] = idx_comp_29;
    idx_comp_vec[99] = idx_comp_28;
    idx_comp_vec[100] = idx_comp_27;
    idx_comp_vec[101] = idx_comp_26;
    idx_comp_vec[102] = idx_comp_25;
    idx_comp_vec[103] = idx_comp_24;
    idx_comp_vec[104] = idx_comp_23;
    idx_comp_vec[105] = idx_comp_22;
    idx_comp_vec[106] = idx_comp_21;
    idx_comp_vec[107] = idx_comp_20;
    idx_comp_vec[108] = idx_comp_19;
    idx_comp_vec[109] = idx_comp_18;
    idx_comp_vec[110] = idx_comp_17;
    idx_comp_vec[111] = idx_comp_16;
    idx_comp_vec[112] = idx_comp_15;
    idx_comp_vec[113] = idx_comp_14;
    idx_comp_vec[114] = idx_comp_13;
    idx_comp_vec[115] = idx_comp_12;
    idx_comp_vec[116] = idx_comp_11;
    idx_comp_vec[117] = idx_comp_10;
    idx_comp_vec[118] = idx_comp_9;
    idx_comp_vec[119] = idx_comp_8;
    idx_comp_vec[120] = idx_comp_7;
    idx_comp_vec[121] = idx_comp_6;
    idx_comp_vec[122] = idx_comp_5;
    idx_comp_vec[123] = idx_comp_4;
    idx_comp_vec[124] = idx_comp_3;
    idx_comp_vec[125] = idx_comp_2;
    idx_comp_vec[126] = idx_comp_1;
    idx_comp_vec[127] = idx_comp_0;
  end

  assign _zz_2 = (~ idx_comp_vec);
  assign lzc = lzc_t[6:0];
  assign position = (lzc - 7'h01);
  assign x1 = _zz_416[31 : 0];
  assign x2 = _zz_417[31 : 0];
  assign y1_real = _zz_23;
  assign y1_imag = _zz_24;
  assign y2_real = _zz_26;
  assign y2_imag = _zz_27;
  assign xd = ($signed(x1) - $signed(x2));
  assign yd_real = ($signed(y1_real) - $signed(y2_real));
  assign yd_imag = ($signed(y1_imag) - $signed(y2_imag));
  assign _zz_9 = yd_real;
  assign _zz_10 = yd_imag;
  assign _zz_11 = xd;
  assign _zz_3 = _zz_418[31 : 0];
  always @ (*) begin
    if(_zz_28)begin
      k_real = _zz_419[31 : 0];
    end else begin
      k_real = _zz_425[31 : 0];
    end
  end

  always @ (*) begin
    if(_zz_28)begin
      k_imag = _zz_420[31 : 0];
    end else begin
      k_imag = _zz_430[31 : 0];
    end
  end

  assign _zz_12 = _zz_421[31 : 0];
  assign _zz_13 = _zz_3;
  assign _zz_14 = ($signed(_zz_422) / $signed(_zz_424));
  assign _zz_15 = _zz_426[31 : 0];
  assign _zz_16 = _zz_3;
  assign _zz_17 = ($signed(_zz_427) / $signed(_zz_429));
  assign deltax_real = ($signed(indx_regNext) - $signed(x1));
  assign deltax_imag = 32'h0;
  assign _zz_4 = ($signed(_zz_431) * $signed(deltax_real));
  assign _zz_18 = _zz_432;
  assign _zz_19 = _zz_435;
  assign pre_exp_indx_real = ($signed(_zz_438) + $signed(_zz_441));
  assign pre_exp_indx_imag = ($signed(_zz_442) + $signed(_zz_445));
  assign _zz_20 = pre_exp_indx_regNext_real;
  always @ (*) begin
    if(_zz_29)begin
      exp_func_value_real = _zz_446[31 : 0];
    end else begin
      if(_zz_30)begin
        exp_func_value_real = _zz_5;
      end else begin
        exp_func_value_real = _zz_7;
      end
    end
  end

  assign _zz_21 = pre_exp_indx_regNext_imag;
  always @ (*) begin
    if(_zz_29)begin
      exp_func_value_imag = _zz_447[31 : 0];
    end else begin
      if(_zz_30)begin
        exp_func_value_imag = _zz_6;
      end else begin
        exp_func_value_imag = _zz_8;
      end
    end
  end

  assign io_data_out_real = exp_func_value_real;
  assign io_data_out_imag = exp_func_value_imag;
  always @ (posedge clk) begin
    lzc_t <= (_zz_34 + _zz_225);
  end

  always @ (posedge clk) begin
    indx_regNext <= indx;
    pre_exp_indx_regNext_real <= pre_exp_indx_real;
    pre_exp_indx_regNext_imag <= pre_exp_indx_imag;
  end

  always @ (posedge clk) begin
    pfunc_tb_127_regNext_real <= pfunc_tb_127_real;
    pfunc_tb_127_regNext_imag <= pfunc_tb_127_imag;
    _zz_5 <= pfunc_tb_127_regNext_real;
    _zz_6 <= pfunc_tb_127_regNext_imag;
  end

  always @ (posedge clk) begin
    pfunc_tb_0_regNext_real <= pfunc_tb_0_real;
    pfunc_tb_0_regNext_imag <= pfunc_tb_0_imag;
    _zz_7 <= pfunc_tb_0_regNext_real;
    _zz_8 <= pfunc_tb_0_regNext_imag;
  end


endmodule
