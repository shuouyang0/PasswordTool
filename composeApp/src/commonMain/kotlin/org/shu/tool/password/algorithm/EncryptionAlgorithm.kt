package org.shu.keytool.base.algorithm

interface IEncryptionAlgorithm{
    fun encrypt(websiteUrl:String,privateKey:String):String
}
object EncryptionAlgorithm:IEncryptionAlgorithm {
    private lateinit var proxy:IEncryptionAlgorithm
    fun use(algorithm: IEncryptionAlgorithm){
        proxy = algorithm
    }

    override fun encrypt(websiteUrl: String, privateKey: String) = proxy.encrypt(websiteUrl,privateKey)

}