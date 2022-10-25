(ns tablecloth.api
  ;;Autogenerated from tablecloth.api.api-template-- DO NOT EDIT
  "Tablecloth API"
  (:require [tablecloth.api.api-template]
            [tablecloth.api.aggregate]
            [tablecloth.api.columns]
            [tablecloth.api.dataset]
            [tablecloth.api.fold-unroll]
            [tablecloth.api.group-by]
            [tablecloth.api.join-concat-ds]
            [tablecloth.api.join-separate]
            [tablecloth.api.missing]
            [tablecloth.api.order-by]
            [tablecloth.api.reshape]
            [tablecloth.api.rows]
            [tablecloth.api.split]
            [tablecloth.api.unique-by]
            [tablecloth.api.utils]
            [tech.v3.dataset]
            [tech.v3.dataset.print]
            [tech.v3.datatype])
  (:refer-clojure :exclude [group-by drop concat rand-nth first last shuffle]))

(defn ->array
  "Convert numerical column(s) to java array"
  ([ds colname]
  (tablecloth.api.columns/->array ds colname))
  ([ds colname datatype]
  (tablecloth.api.columns/->array ds colname datatype)))


(defn add-column
  "Add or update (modify) column under `column-name`.

  `column` can be sequence of values or generator function (which gets `ds` as input).

  * `ds` - a dataset
  * `column-name` - if it's existing column name, column will be replaced
  * `column` - can be column (from other dataset), sequence, single value or function (taking a dataset). Too big columns are always trimmed. Too small are cycled or extended with missing values (according to `size-strategy` argument)
  * `size-strategy` (optional) - when new column is shorter than dataset row count, following strategies are applied:
    - `:cycle` - repeat data
    - `:na` - append missing values
    - `:strict` - (default) throws an exception when sizes mismatch"
  ([ds column-name column]
  (tablecloth.api.columns/add-column ds column-name column))
  ([ds column-name column size-strategy]
  (tablecloth.api.columns/add-column ds column-name column size-strategy)))


(defn add-columns
  "Add or updade (modify) columns defined in `columns-map` (mapping: name -> column) "
  ([ds columns-map]
  (tablecloth.api.columns/add-columns ds columns-map))
  ([ds columns-map size-strategy]
  (tablecloth.api.columns/add-columns ds columns-map size-strategy)))


(defn add-or-replace-column
  ([ds column-name column]
  (tablecloth.api.columns/add-or-replace-column ds column-name column))
  ([ds column-name column size-strategy]
  (tablecloth.api.columns/add-or-replace-column ds column-name column size-strategy)))


(defn add-or-replace-columns
  ([ds columns-map]
  (tablecloth.api.columns/add-or-replace-columns ds columns-map))
  ([ds columns-map size-strategy]
  (tablecloth.api.columns/add-or-replace-columns ds columns-map size-strategy)))


(defn aggregate
  "Aggregate dataset by providing:

  - aggregation function
  - map with column names and functions
  - sequence of aggregation functions

  Aggregation functions can return:
  - single value
  - seq of values
  - map of values with column names"
  ([ds aggregator]
  (tablecloth.api.aggregate/aggregate ds aggregator))
  ([ds aggregator options]
  (tablecloth.api.aggregate/aggregate ds aggregator options)))


(defn aggregate-columns
  "Aggregates each column separately"
  ([ds columns-selector column-aggregators]
  (tablecloth.api.aggregate/aggregate-columns ds columns-selector column-aggregators))
  ([ds columns-selector column-aggregators options]
  (tablecloth.api.aggregate/aggregate-columns ds columns-selector column-aggregators options)))


(defn anti-join
  ([ds-left ds-right columns-selector]
  (tablecloth.api.join-concat-ds/anti-join ds-left ds-right columns-selector))
  ([ds-left ds-right columns-selector options]
  (tablecloth.api.join-concat-ds/anti-join ds-left ds-right columns-selector options)))


(defn append
  "Concats columns of several datasets"
  ([ds & args]
  (apply tablecloth.api.join-concat-ds/append ds args)))


(defn array-column->columns
  ([ds column]
  (tablecloth.api.join-separate/array-column->columns ds column)))


(defn as-regular-dataset
  "Remove grouping tag"
  ([ds]
  (tablecloth.api.utils/as-regular-dataset ds)))


(defn asof-join
  ([ds-left ds-right columns-selector]
  (tablecloth.api.join-concat-ds/asof-join ds-left ds-right columns-selector))
  ([ds-left ds-right columns-selector options]
  (tablecloth.api.join-concat-ds/asof-join ds-left ds-right columns-selector options)))


(defn bind
  ([ds & args]
  (apply tablecloth.api.join-concat-ds/bind ds args)))


(defn by-rank
  "Select rows using `rank` on a column, ties are resolved using `:dense` method.

  See [R docs](https://www.rdocumentation.org/packages/base/versions/3.6.1/topics/rank).
  Rank uses 0 based indexing.
  
  Possible `:ties` strategies: `:average`, `:first`, `:last`, `:random`, `:min`, `:max`, `:dense`.
  `:dense` is the same as in `data.table::frank` from R

  `:desc?` set to true (default) order descending before calculating rank"
  ([ds columns-selector rank-predicate]
  (tablecloth.api.rows/by-rank ds columns-selector rank-predicate))
  ([ds columns-selector rank-predicate options]
  (tablecloth.api.rows/by-rank ds columns-selector rank-predicate options)))


(defn clone
  "Clone an object.  Can clone anything convertible to a reader."
  ([item]
  (tech.v3.datatype/clone item)))


(defn column
  ([dataset colname]
  (tech.v3.dataset/column dataset colname)))


(defn column-count
  (^{:tag long} [dataset]
  (tech.v3.dataset/column-count dataset)))


(defn column-names
  "Returns column names, given a selector.
  Columns-selector can be one of the following:

  * :all keyword - selects all columns
  * column name - for single column
  * sequence of column names - for collection of columns
  * regex - to apply pattern on column names or datatype
  * filter predicate - to filter column names or datatype
  * type namespaced keyword for specific datatype or group of datatypes

  Column name can be anything.

column-names function returns names according to columns-selector
  and optional meta-field. meta-field is one of the following:

  * `:name` (default) - to operate on column names
  * `:datatype` - to operated on column types
  * `:all` - if you want to process all metadata

  Datatype groups are:

  * `:type/numerical` - any numerical type
  * `:type/float` - floating point number (:float32 and :float64)
  * `:type/integer` - any integer
  * `:type/datetime` - any datetime type

  If qualified keyword starts with :!type, complement set is used.


  "
  ([ds]
  (tablecloth.api.utils/column-names ds))
  ([ds columns-selector]
  (tablecloth.api.utils/column-names ds columns-selector))
  ([ds columns-selector meta-field]
  (tablecloth.api.utils/column-names ds columns-selector meta-field)))


(defn columns
  "Returns columns of dataset. Result type can be any of:
  * `:as-map`
  * `:as-double-arrays`
  * `:as-seqs`
  "
  ([ds]
  (tablecloth.api.dataset/columns ds))
  ([ds result-type]
  (tablecloth.api.dataset/columns ds result-type)))


(defn columns->array-column
  ([ds column-selector new-column]
  (tablecloth.api.join-separate/columns->array-column ds column-selector new-column)))


(defn complete
  "TidyR complete.

  Fills a dataset with all possible combinations of selected columns. When a given combination doesn't exist, missing values are created."
  ([ds columns-selector & args]
  (apply tablecloth.api.join-concat-ds/complete ds columns-selector args)))


(defn concat
  "Joins rows from other datasets"
  ([dataset & args]
  (apply tablecloth.api.dataset/concat dataset args)))


(defn concat-copying
  "Joins rows from other datasets via a copy of data"
  ([dataset & args]
  (apply tablecloth.api.dataset/concat-copying dataset args)))


(defn convert-types
  "Convert type of the column to the other type."
  ([ds coltype-map-or-columns-selector]
  (tablecloth.api.columns/convert-types ds coltype-map-or-columns-selector))
  ([ds columns-selector new-types]
  (tablecloth.api.columns/convert-types ds columns-selector new-types)))


(defn cross-join
  "Cross product from selected columns"
  ([ds-left ds-right]
  (tablecloth.api.join-concat-ds/cross-join ds-left ds-right))
  ([ds-left ds-right columns-selector]
  (tablecloth.api.join-concat-ds/cross-join ds-left ds-right columns-selector))
  ([ds-left ds-right columns-selector options]
  (tablecloth.api.join-concat-ds/cross-join ds-left ds-right columns-selector options)))


(defn crosstab
  "Cross tabulation of two sets of columns.

  Creates grouped dataset by [row-selector, col-selector] pairs and calls aggregation on each group.

  Options:

  * pivot? - create pivot table or just flat structure (default: true)
  * replace-missing? - replace missing values? (default: true)
  * missing-value - a missing value (default: 0)
  * aggregator - aggregating function (default: row-count)
  * marginal-rows, marginal-cols - adds row and/or cols, it's a sum if true. Can be a custom fn."
  ([ds row-selector col-selector]
  (tablecloth.api.aggregate/crosstab ds row-selector col-selector))
  ([ds row-selector col-selector options]
  (tablecloth.api.aggregate/crosstab ds row-selector col-selector options)))


(defn dataset
  "Create `dataset`.
  
  Dataset can be created from:

  * map of values and/or sequences
  * sequence of maps
  * sequence of columns
  * file or url
  * array of arrays
  * single value

  Single value is set only when it's not possible to find a path for given data. If tech.ml.dataset throws an exception, it's won;t be printed. To print a stack trace, set `stack-trace?` option to `true`."
  ([]
  (tablecloth.api.dataset/dataset ))
  ([data]
  (tablecloth.api.dataset/dataset data))
  ([data options]
  (tablecloth.api.dataset/dataset data options)))


(defn dataset->str
  "Convert a dataset to a string.  Prints a single line header and then calls
  dataset-data->str.

  For options documentation see dataset-data->str."
  ([ds options]
  (tech.v3.dataset.print/dataset->str ds options))
  ([ds]
  (tech.v3.dataset.print/dataset->str ds)))


(defn dataset-name
  ([dataset]
  (tech.v3.dataset/dataset-name dataset)))


(defn dataset?
  "Is `ds` a `dataset` type?"
  ([ds]
  (tablecloth.api.dataset/dataset? ds)))


(defn difference
  ([ds-left ds-right]
  (tablecloth.api.join-concat-ds/difference ds-left ds-right))
  ([ds-left ds-right options]
  (tablecloth.api.join-concat-ds/difference ds-left ds-right options)))


(defn drop
  "Drop columns and rows."
  ([ds columns-selector rows-selector]
  (tablecloth.api.api-template/drop ds columns-selector rows-selector)))


(defn drop-columns
  "Drop columns by (returns dataset):

  - name
  - sequence of names
  - map of names with new names (rename)
  - function which filter names (via column metadata)"
  ([ds]
  (tablecloth.api.columns/drop-columns ds))
  ([ds columns-selector]
  (tablecloth.api.columns/drop-columns ds columns-selector))
  ([ds columns-selector meta-field]
  (tablecloth.api.columns/drop-columns ds columns-selector meta-field)))


(defn drop-missing
  "Drop rows with missing values

 `columns-selector` selects columns to look at missing values"
  ([ds]
  (tablecloth.api.missing/drop-missing ds))
  ([ds columns-selector]
  (tablecloth.api.missing/drop-missing ds columns-selector)))


(defn drop-rows
  "Drop rows using:

  - row id
  - seq of row ids
  - seq of true/false
  - fn with predicate"
  ([ds]
  (tablecloth.api.rows/drop-rows ds))
  ([ds rows-selector]
  (tablecloth.api.rows/drop-rows ds rows-selector))
  ([ds rows-selector options]
  (tablecloth.api.rows/drop-rows ds rows-selector options)))


(defn empty-ds?
  ([ds]
  (tablecloth.api.dataset/empty-ds? ds)))


(defn expand
  "TidyR expand.

  Creates all possible combinations of selected columns."
  ([ds columns-selector & args]
  (apply tablecloth.api.join-concat-ds/expand ds columns-selector args)))


(defn fill-range-replace
  "Fill missing up with lacking values. Accepts
  * dataset
  * column name
  * expected step (max-span, milliseconds in case of datetime column)
  * (optional) missing-strategy - how to replace missing, default :down (set to nil if none)
  * (optional) missing-value - optional value for replace missing
"
  ([ds colname max-span]
  (tablecloth.api.missing/fill-range-replace ds colname max-span))
  ([ds colname max-span missing-strategy]
  (tablecloth.api.missing/fill-range-replace ds colname max-span missing-strategy))
  ([ds colname max-span missing-strategy missing-value]
  (tablecloth.api.missing/fill-range-replace ds colname max-span missing-strategy missing-value)))


(defn first
  "First row"
  ([ds]
  (tablecloth.api.rows/first ds)))


(defn fold-by
  "Group-by and pack columns into vector - the output data set has a row for each unique combination
  of the provided columns while each remaining column has its valu(es) collected into a vector, similar
  to how clojure.core/group-by works.
  See https://scicloj.github.io/tablecloth/index.html#Fold-by"
  ([ds columns-selector]
  (tablecloth.api.fold-unroll/fold-by ds columns-selector))
  ([ds columns-selector folding-function]
  (tablecloth.api.fold-unroll/fold-by ds columns-selector folding-function)))


(defn full-join
  "Join keeping all rows"
  ([ds-left ds-right columns-selector]
  (tablecloth.api.join-concat-ds/full-join ds-left ds-right columns-selector))
  ([ds-left ds-right columns-selector options]
  (tablecloth.api.join-concat-ds/full-join ds-left ds-right columns-selector options)))


(defn get-entry
  "Returns a single value from given column and row"
  ([ds column row]
  (tablecloth.api.dataset/get-entry ds column row)))


(defn group-by
  "Group dataset by:

  - column name
  - list of columns
  - map of keys and row indexes
  - function getting map of values

  Options are:

  - select-keys - when grouping is done by function, you can limit fields to a `select-keys` seq.
  - result-type - return results as dataset (`:as-dataset`, default) or as map of datasets (`:as-map`) or as map of row indexes (`:as-indexes`) or as sequence of (sub)datasets
  - other parameters which are passed to `dataset` fn

  When dataset is returned, meta contains `:grouped?` set to true. Columns in dataset:

  - name - group name
  - group-id - id of the group (int)
  - data - group as dataset"
  ([ds grouping-selector]
  (tablecloth.api.group-by/group-by ds grouping-selector))
  ([ds grouping-selector options]
  (tablecloth.api.group-by/group-by ds grouping-selector options)))


(defn grouped?
  "Is `dataset` represents grouped dataset (result of `group-by`)?"
  ([ds]
  (tablecloth.api.utils/grouped? ds)))


(defn groups->map
  "Convert grouped dataset to the map of groups"
  ([ds]
  (tablecloth.api.group-by/groups->map ds)))


(defn groups->seq
  "Convert grouped dataset to seq of the groups"
  ([ds]
  (tablecloth.api.group-by/groups->seq ds)))


(defn has-column?
  ([dataset column-name]
  (tech.v3.dataset/has-column? dataset column-name)))


(defn head
  "First n rows (default 5)"
  ([ds]
  (tablecloth.api.rows/head ds))
  ([ds n]
  (tablecloth.api.rows/head ds n)))


(defn info
  "Returns a statistcial information about the columns of a dataset.
  `result-type ` can be :descriptive or :columns
  "
  ([ds]
  (tablecloth.api.dataset/info ds))
  ([ds result-type]
  (tablecloth.api.dataset/info ds result-type)))


(defn inner-join
  ([ds-left ds-right columns-selector]
  (tablecloth.api.join-concat-ds/inner-join ds-left ds-right columns-selector))
  ([ds-left ds-right columns-selector options]
  (tablecloth.api.join-concat-ds/inner-join ds-left ds-right columns-selector options)))


(defn intersect
  ([ds-left ds-right]
  (tablecloth.api.join-concat-ds/intersect ds-left ds-right))
  ([ds-left ds-right options]
  (tablecloth.api.join-concat-ds/intersect ds-left ds-right options)))


(defn join-columns
  "Join clumns of dataset. Accepts:
dataset
column selector (as in select-columns)
options
  `:separator` (default -)
  `:drop-columns?` - whether to drop source columns or not (default true)
  `:result-type`
     `:map` - packs data into map
     `:seq` - packs data into sequence
     `:string` - join strings with separator (default)
     or custom function which gets row as a vector
  `:missing-subst` - substitution for missing value
  "
  ([ds target-column columns-selector]
  (tablecloth.api.join-separate/join-columns ds target-column columns-selector))
  ([ds target-column columns-selector conf]
  (tablecloth.api.join-separate/join-columns ds target-column columns-selector conf)))


(defn last
  "Last row"
  ([ds]
  (tablecloth.api.rows/last ds)))


(defn left-join
  ([ds-left ds-right columns-selector]
  (tablecloth.api.join-concat-ds/left-join ds-left ds-right columns-selector))
  ([ds-left ds-right columns-selector options]
  (tablecloth.api.join-concat-ds/left-join ds-left ds-right columns-selector options)))


(defmacro let-dataset
  ([bindings]
  `(tablecloth.api.api-template/let-dataset ~bindings))
  ([bindings options]
  `(tablecloth.api.api-template/let-dataset ~bindings ~options)))


(defn map-columns
  "Map over rows using a map function. The arity should match the columns selected."
  ([ds column-name map-fn]
  (tablecloth.api.columns/map-columns ds column-name map-fn))
  ([ds column-name columns-selector map-fn]
  (tablecloth.api.columns/map-columns ds column-name columns-selector map-fn))
  ([ds column-name new-type columns-selector map-fn]
  (tablecloth.api.columns/map-columns ds column-name new-type columns-selector map-fn)))


(defn mark-as-group
  "Add grouping tag"
  ([ds]
  (tablecloth.api.utils/mark-as-group ds)))


(defn order-by
  "Order dataset by:
  - column name
  - columns (as sequence of names)
  - key-fn
  - sequence of columns / key-fn
  Additionally you can ask the order by:
  - :asc
  - :desc
  - custom comparator function"
  ([ds columns-or-fn]
  (tablecloth.api.order-by/order-by ds columns-or-fn))
  ([ds columns-or-fn comparators]
  (tablecloth.api.order-by/order-by ds columns-or-fn comparators))
  ([ds columns-or-fn comparators options]
  (tablecloth.api.order-by/order-by ds columns-or-fn comparators options)))


(defn pivot->longer
  "`tidyr` pivot_longer api"
  ([ds]
  (tablecloth.api.reshape/pivot->longer ds))
  ([ds columns-selector]
  (tablecloth.api.reshape/pivot->longer ds columns-selector))
  ([ds columns-selector options]
  (tablecloth.api.reshape/pivot->longer ds columns-selector options)))


(defn pivot->wider
  "Converts columns to rows. Arguments:
  * dataset
  * columns selector
  * options:
    `:target-columns` - names of the columns created or columns pattern (see below) (default: :$column)
    `:value-column-name` - name of the column for values (default: :$value)
    `:splitter` - string, regular expression or function which splits source column names into data
    `:drop-missing?` - remove rows with missing? (default: true)
    `:datatypes` - map of target columns data types
    `:coerce-to-number` - try to convert extracted values to numbers if possible (default: true)

  * target-columns - can be:

    * column name - source columns names are put there as a data
    * column names as seqence - source columns names after split are put separately into :target-columns as data
    * pattern - is a sequence of names, where some of the names are nil. nil is replaced by a name taken from splitter and such column is used for values.
  "
  ([ds columns-selector value-columns]
  (tablecloth.api.reshape/pivot->wider ds columns-selector value-columns))
  ([ds columns-selector value-columns options]
  (tablecloth.api.reshape/pivot->wider ds columns-selector value-columns options)))


(defn print-dataset
  "Prints dataset into console. For options see
  tech.v3.dataset.print/dataset-data->str
"
  ([ds]
  (tablecloth.api.dataset/print-dataset ds))
  ([ds options]
  (tablecloth.api.dataset/print-dataset ds options)))


(defn process-group-data
  "Internal: The passed-in function is applied on all groups"
  ([ds f]
  (tablecloth.api.utils/process-group-data ds f))
  ([ds f parallel?]
  (tablecloth.api.utils/process-group-data ds f parallel?)))


(defn rand-nth
  "Returns single random row"
  ([ds]
  (tablecloth.api.rows/rand-nth ds))
  ([ds options]
  (tablecloth.api.rows/rand-nth ds options)))


(defn random
  "Returns (n) random rows with repetition"
  ([ds]
  (tablecloth.api.rows/random ds))
  ([ds n]
  (tablecloth.api.rows/random ds n))
  ([ds n options]
  (tablecloth.api.rows/random ds n options)))


(defn read-nippy
  ([filename]
  (tablecloth.api.utils/read-nippy filename)))


(defn rename-columns
  "Rename columns with provided old -> new name map"
  ([ds columns-selector columns-map-fn]
  (tablecloth.api.columns/rename-columns ds columns-selector columns-map-fn))
  ([ds columns-mapping]
  (tablecloth.api.columns/rename-columns ds columns-mapping)))


(defn reorder-columns
  "Reorder columns using column selector(s). When column names are incomplete, the missing will be attached at the end."
  ([ds columns-selector & args]
  (apply tablecloth.api.columns/reorder-columns ds columns-selector args)))


(defn replace-missing
  "Replaces missing values. Accepts

  * dataset
  * column selector, default: :all
  * strategy, default: :nearest
  * value (optional)
  * single value
  * sequence of values (cycled)
  * function, applied on column(s) with stripped missings

  Strategies are:

  `:value` - replace with given value
  `:up` - copy values up
  `:down` - copy values down
  `:updown` - copy values up and then down for missing values at the end
  `:downup` - copy values down and then up for missing values at the beginning
  `:mid` or `:nearest` - copy values around known values
  `:midpoint` - use average value from previous and next non-missing
  `:lerp` - trying to lineary approximate values, works for numbers and datetime, otherwise applies :nearest. For numbers always results in float datatype.
  "
  ([ds]
  (tablecloth.api.missing/replace-missing ds))
  ([ds strategy]
  (tablecloth.api.missing/replace-missing ds strategy))
  ([ds columns-selector strategy]
  (tablecloth.api.missing/replace-missing ds columns-selector strategy))
  ([ds columns-selector strategy value]
  (tablecloth.api.missing/replace-missing ds columns-selector strategy value)))


(defn right-join
  ([ds-left ds-right columns-selector]
  (tablecloth.api.join-concat-ds/right-join ds-left ds-right columns-selector))
  ([ds-left ds-right columns-selector options]
  (tablecloth.api.join-concat-ds/right-join ds-left ds-right columns-selector options)))


(defn row-count
  (^{:tag long} [dataset-or-col]
  (tech.v3.dataset/row-count dataset-or-col)))


(defn rows
  "Returns rows of dataset. Result type can be any of:
  * `:as-maps`
  * `:as-double-arrays`
  * `:as-seqs`
  "
  ([ds]
  (tablecloth.api.dataset/rows ds))
  ([ds result-type]
  (tablecloth.api.dataset/rows ds result-type)))


(defn select
  "Select columns and rows."
  ([ds columns-selector rows-selector]
  (tablecloth.api.api-template/select ds columns-selector rows-selector)))


(defn select-columns
  "Select columns by (returns dataset):

  - name
  - sequence of names
  - map of names with new names (rename)
  - function which filter names (via column metadata)"
  ([ds]
  (tablecloth.api.columns/select-columns ds))
  ([ds columns-selector]
  (tablecloth.api.columns/select-columns ds columns-selector))
  ([ds columns-selector meta-field]
  (tablecloth.api.columns/select-columns ds columns-selector meta-field)))


(defn select-missing
  "Select rows with missing values

 `columns-selector` selects columns to look at missing values"
  ([ds]
  (tablecloth.api.missing/select-missing ds))
  ([ds columns-selector]
  (tablecloth.api.missing/select-missing ds columns-selector)))


(defn select-rows
  "Select rows using:

  - row id
  - seq of row ids
  - seq of true/false
  - fn with predicate"
  ([ds]
  (tablecloth.api.rows/select-rows ds))
  ([ds rows-selector]
  (tablecloth.api.rows/select-rows ds rows-selector))
  ([ds rows-selector options]
  (tablecloth.api.rows/select-rows ds rows-selector options)))


(defn semi-join
  ([ds-left ds-right columns-selector]
  (tablecloth.api.join-concat-ds/semi-join ds-left ds-right columns-selector))
  ([ds-left ds-right columns-selector options]
  (tablecloth.api.join-concat-ds/semi-join ds-left ds-right columns-selector options)))


(defn separate-column
  ([ds column]
  (tablecloth.api.join-separate/separate-column ds column))
  ([ds column separator]
  (tablecloth.api.join-separate/separate-column ds column separator))
  ([ds column target-columns separator]
  (tablecloth.api.join-separate/separate-column ds column target-columns separator))
  ([ds column target-columns separator conf]
  (tablecloth.api.join-separate/separate-column ds column target-columns separator conf)))


(defn set-dataset-name
  ([dataset ds-name]
  (tech.v3.dataset/set-dataset-name dataset ds-name)))


(defn shape
  "Returns shape of the dataset [rows, cols]"
  ([ds]
  (tablecloth.api.dataset/shape ds)))


(defn shuffle
  "Shuffle dataset (with seed)"
  ([ds]
  (tablecloth.api.rows/shuffle ds))
  ([ds options]
  (tablecloth.api.rows/shuffle ds options)))


(defn split
  "Split given dataset into 2 or more (holdout) splits

  As the result two new columns are added:

  * `:$split-name` - with subgroup name
  * `:$split-id` - fold id/repetition id

  `split-type` can be one of the following:

  * `:kfold` - k-fold strategy, `:k` defines number of folds (defaults to `5`), produces `k` splits
  * `:bootstrap` - `:ratio` defines ratio of observations put into result (defaults to `1.0`), produces `1` split
  * `:holdout` - split into two parts with given ratio (defaults to `2/3`), produces `1` split
  * `:loo` - leave one out, produces the same number of splits as number of observations

  `:holdout` can accept also probabilites or ratios and can split to more than 2 subdatasets
  
  Additionally you can provide:

  * `:seed` - for random number generator
  * `:repeats` - repeat procedure `:repeats` times
  * `:partition-selector` - same as in `group-by` for stratified splitting to reflect dataset structure in splits.
  * `:split-names` names of subdatasets different than default, ie. `[:train :test :split-2 ...]`
  * `:split-col-name` - a column where name of split is stored, either `:train` or `:test` values (default: `:$split-name`)
  * `:split-id-col-name` - a column where id of the train/test pair is stored (default: `:$split-id`)

  Rows are shuffled before splitting.
  
  In case of grouped dataset each group is processed separately.

  See [more](https://www.mitpressjournals.org/doi/pdf/10.1162/EVCO_a_00069)"
  ([ds]
  (tablecloth.api.split/split ds))
  ([ds split-type]
  (tablecloth.api.split/split ds split-type))
  ([ds split-type options]
  (tablecloth.api.split/split ds split-type options)))


(defn split->seq
  "Returns split as a sequence of train/test datasets or map of sequences (grouped dataset)"
  ([ds]
  (tablecloth.api.split/split->seq ds))
  ([ds split-type]
  (tablecloth.api.split/split->seq ds split-type))
  ([ds split-type options]
  (tablecloth.api.split/split->seq ds split-type options)))


(defn tail
  "Last n rows (default 5)"
  ([ds]
  (tablecloth.api.rows/tail ds))
  ([ds n]
  (tablecloth.api.rows/tail ds n)))


(defn ungroup
  "Concat groups into dataset.

  When `add-group-as-column` or `add-group-id-as-column` is set to `true` or name(s), columns with group name(s) or group id is added to the result.

  Before joining the groups groups can be sorted by group name."
  ([ds]
  (tablecloth.api.group-by/ungroup ds))
  ([ds options]
  (tablecloth.api.group-by/ungroup ds options)))


(defn union
  ([ds & args]
  (apply tablecloth.api.join-concat-ds/union ds args)))


(defn unique-by
  "Remove rows which contains the same data
  `column-selector` Select columns for uniqueness
  `strategy` There are 4 strategies defined to handle duplicates

    `:first` - select first row (default)
    `:last` - select last row
    `:random` - select random row
    any function - apply function to a columns which are subject of uniqueness"
  ([ds]
  (tablecloth.api.unique-by/unique-by ds))
  ([ds columns-selector]
  (tablecloth.api.unique-by/unique-by ds columns-selector))
  ([ds columns-selector options]
  (tablecloth.api.unique-by/unique-by ds columns-selector options)))


(defn unmark-group
  "Remove grouping tag"
  ([ds]
  (tablecloth.api.utils/unmark-group ds)))


(defn unroll
  "Unfolds sequences stored inside a column(s), turning it into multiple columns. Opposite of [[fold-by]].
  Add each of the provided columns to the set that defines the \"uniqe key\" of each row.
  Thus there will be a new row for each value inside the target column(s)' value sequence.
  If you want instead to split the content of the columns into a set of new _columns_, look at [[separate-column]].
  See https://scicloj.github.io/tablecloth/index.html#Unroll"
  ([ds columns-selector]
  (tablecloth.api.fold-unroll/unroll ds columns-selector))
  ([ds columns-selector options]
  (tablecloth.api.fold-unroll/unroll ds columns-selector options)))


(defn update-columns
  ([ds columns-map]
  (tablecloth.api.columns/update-columns ds columns-map))
  ([ds columns-selector update-functions]
  (tablecloth.api.columns/update-columns ds columns-selector update-functions)))


(defmacro without-grouping->
  ([ds & args]
  `(tablecloth.api.api-template/without-grouping-> ~ds ~@args)))


(defn write!
  "Write a dataset out to a file.  Supported forms are:

```clojure
(ds/write! test-ds \"test.csv\")
(ds/write! test-ds \"test.tsv\")
(ds/write! test-ds \"test.tsv.gz\")
(ds/write! test-ds \"test.nippy\")
(ds/write! test-ds out-stream)
```

Options:

  * `:max-chars-per-column` - csv,tsv specific, defaults to 65536 - values longer than this will
     cause an exception during serialization.
  * `:max-num-columns` - csv,tsv specific, defaults to 8192 - If the dataset has more than this number of
     columns an exception will be thrown during serialization.
  * `:quoted-columns` - csv specific - sequence of columns names that you would like to always have quoted.
  * `:file-type` - Manually specify the file type.  This is usually inferred from the filename but if you
     pass in an output stream then you will need to specify the file type.
  * `:headers?` - if csv headers are written, defaults to true."
  ([dataset output-path options]
  (tech.v3.dataset/write! dataset output-path options))
  ([dataset output-path]
  (tech.v3.dataset/write! dataset output-path)))


(def write-csv! tablecloth.api.api-template/write-csv!)
(defn write-nippy!
  ([ds filename]
  (tablecloth.api.utils/write-nippy! ds filename)))


