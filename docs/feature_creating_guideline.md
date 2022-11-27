## Feature creating guideline

### for UI feature:
1. create Android library module with path __com.mityushovn.miningquiz.FEATURE_NAME_FOLDER__.
2. configure module's __build.gradle__ file(clean up, set the compile and target SDK, add _:core-injector_ etc.).
3. in _com.mityushovn.miningquiz.FEATURE_NAME_FOLDER_ create 2 folders: __API__ and __internal__.
The __API__ folder contains public interfaces that module provides. For UI features api is Fragment, and
an interface that implements Dependencies interface from _:core-injector_ module, it needs for Dagger
or any else DI framework.
The __internal__ folder contains all module private classes.<br />
       com.mityushov.miningquiz.FEATURE_NAME_FOLDER
                                              |_ API
                                              |    |_ FEATURE_NAME_DEPS.kt
                                              |    |
                                              |    |_ FEATURE_NAME_FRAGMENT.kt
                                              |_ internal
4. create public __FEATURE_NAME_Deps__ interface and implements _com.mityushovn.miningquiz.module_injector.interfaces.Dependencies_ interface
and define all dependencies that needs module.
Example:<br />
interface StatisticsDeps: Dependencies {<br />
   val db: SQLiteDatabase<br />
}<br />
