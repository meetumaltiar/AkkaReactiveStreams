package specification

trait Publisher[T] {
  def subscribe(sub: Subscriber[T]): Unit
}

trait Subscription {
  def requestMore(n: Int): Unit
  def cancel(): Unit
}

trait Subscriber[T] {
  def onSubscribe(s: Subscription): Unit
  def onNext(elem: T): Unit
  def onError(thr: Throwable): Unit
  def onComplete(): Unit
}