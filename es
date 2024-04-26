es查询
match查询
text是通过分词器分过词的，
match_phrase
它不是匹配到某一处分词的结果就算是匹配成功了，而是需要query中所有的词都匹配到，而且相对顺序还要一致，而且默认还是连续的，
slop

term
term查询也是比较常用的一种查询方式，它和match的唯一区别就是match需要对query进行分词，而term是不会进行分词的
 terms查询
terms 查询默认使用 OR 运算符来组合多个查询条件。

fuzzy查询
fuzzy是ES里面的模糊搜索，它可以借助term查询来进行理解。fuzzy和term一样，也不会将query进行分词，但是不同的是它在进行匹配时可以容忍你的词语拼写有错误，至于容忍度如何，是根据参数fuzziness决定的。fuzziness默认是2，

bool查询
bool查询是上面查询的一个综合，它可以用多个上面的查询去组合出一个大的查询语句，它也有一些关键字：

es分词器
字符过滤器 替换 正则 过滤
分词器（Tokenizer）
根据不同规则把单词分出来
Token Filter） 同义词等
Es shard->lucena index->lucena segment
内存索引缓冲区到刷新文件缓存（refresh） 定时刷新文件（fsync）

When the index refreshes, the documents in the buffer are added to a new Lucene segment which is also held in memory.  
Flushing is the process that stores the in-memory segments onto disk, and simultaneously closes the translog generation and starts with a new blank translog generation.
https://opster.com/guides/elasticsearch/glossary/elasticsearch-flush-translog-and-refresh/#:~:text=When%20the%20index%20refreshes%2C%20the%20documents%20in%20the,and%20starts%20with%20a%20new%20blank%20translog%20generation.
https://stackoverflow.com/questions/19963406/refresh-vs-flush
1. refresh: transform in-memory buffer to in-memory segment which can be searched.
2. flush: (a) merge small segments to be a big segment (b) fsync the big segment to disk (c) empty translog.
The flush() method is primarily used to force any buffered data to be written immediately without closing the FileWriter, while the close() method both performs flushing and releases associated resources.

elasticsearch
读写
索引新文档

协调节点 计算实际存储的多个节点然后查询汇聚
节点写入新文档translog，每秒refresh,写入file cache 构成分段
30分钟或者变大，segment写入磁盘 fsync，

节点 集群 高可用 拓展性 
分片 副本 数据冗余 故障恢复 负载均衡
协调节点 排序 分页 分发请求与收集响应
写入与持久化
事务日志translog buffer2 refresh lucena segment flush  data translog 2 disk
Segment merge 
数据同步是异步进行的
新的段会被添加到索引中，使得新写入的数据可以被搜索到。

优化的数据结构
FST（Finite State Transducer）前缀树
单Term、Term范围、Term前缀和通配符查询
倒排索引：保存了每个term对应的docId的列表，采用skipList的结构保存，用于快速跳跃。
BKD-Tree：BKD-Tree是一种保存多维空间点的数据结构
字段存储
_source Field 
业务字段的store 都会被存到 _source
默认通过 index.codec 压缩算法进行压缩。查询时需要解压。
doc_value Fields：类似于大数据场景中的列存，按列存储，
Stored Fields ：类似于MySQL 的行存，按行存储，

规划
集群总分片数建议控制在5w以内，单个索引的规模控制在 1TB 以内，单个分片大小控制在30 ~ 50GB ，docs数控制在10亿内，如果超过建议滚动；
分片的数量通常建议小于或等于ES 的数据节点数量，最大不超过总节点数的2倍，通过增加分片数可以提升并发

Search Scroll 快快照不可变 系统scrolled 小号内存
Search after 动态 可变 sorted 唯一不重复
ES 默认限制 from + size < 10000
当字段数很少时，低于 40 时，使用 doc_value Fields 拉取，性能最优
当字段超较多时，达到 40 以上时，使用 _source 变为最优。
https://zhuanlan.zhihu.com/p/647279604

场景
全文检索 和 复杂查询。
Term Dictionary）B+树 + 倒排列表（Posting List）。
* 文档ID，等同于数据库主键；
* 词频（Term Frequency），该单词在文档中出现的次数，主要是用于打分；
* 位置（Positon），单词在文档中分词的位置，用于语句搜索；
* 偏移（Offset），记录单词的的位置
高效搜索核心是并行计算和倒排索引。
无法支持复杂查询：任意列的相互组合查询。

