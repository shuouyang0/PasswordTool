package org.shu.tool.password.base.db
import com.ctrip.sqllin.dsl.Database
import com.ctrip.sqllin.dsl.sql.clause.EQ
import com.ctrip.sqllin.dsl.sql.clause.ORDER_BY
import com.ctrip.sqllin.dsl.sql.clause.OrderByWay.DESC
import com.ctrip.sqllin.dsl.sql.clause.SET
import com.ctrip.sqllin.dsl.sql.clause.WHERE
import com.ctrip.sqllin.dsl.sql.statement.SelectStatement
import org.shu.tool.password.base.module.PasswordRecord
import org.shu.tool.password.module.PasswordRecordTable

class PasswordRecordDao(private val database: Database){
    fun obtainAll():List<PasswordRecord>{
        lateinit var selectStatement: SelectStatement<PasswordRecord>
        database{
            PasswordRecordTable{ table: PasswordRecordTable ->
                selectStatement = table SELECT ORDER_BY (registerDate to DESC)
            }
        }
        return selectStatement.getResults()
    }
    fun insert(records: List<PasswordRecord>){
        database{
            PasswordRecordTable{ table: PasswordRecordTable ->
                table INSERT records
            }
        }
    }

    fun delete(targetId:Long) {
        database {
            PasswordRecordTable { table: PasswordRecordTable ->
                table DELETE WHERE (id EQ targetId)
            }
        }
    }

    fun update(record: PasswordRecord){
        database{
            PasswordRecordTable{ table: PasswordRecordTable ->
                table UPDATE SET {
                    websiteLink = record.websiteLink
                    account = record.account
                    accountType = record.accountType
                    passwordType = record.passwordType
                    cipher = record.cipher
                    registerDate = record.registerDate
                    nickname = record.nickname
                    username = record.username
                    modifyDate = record.modifyDate
                    remark = record.remark
                } WHERE (id EQ record.id!!)
            }
        }
    }
}
